package dev.miguel.screenmatch.main;

import java.util.*;
import java.util.stream.Collectors;

import javax.swing.text.html.Option;

import dev.miguel.screenmatch.model.Episode;
import dev.miguel.screenmatch.model.Genres;
import dev.miguel.screenmatch.model.SeasonData;
import dev.miguel.screenmatch.model.Serie;
import dev.miguel.screenmatch.model.SeriesData;
import dev.miguel.screenmatch.repository.SerieRepository;
import dev.miguel.screenmatch.service.DataConverter;
import dev.miguel.screenmatch.service.UseApi;

public class Main {

    private Scanner reader = new Scanner(System.in);
    private UseApi useApi = new UseApi();
    private DataConverter converter = new DataConverter();
    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private List<SeriesData> seriesData = new ArrayList<>();
    private SerieRepository repository;
    private List<Serie> series = new ArrayList<>();
    private Optional<Serie> searchSerie;

    public Main(SerieRepository repository) {
        this.repository = repository;
    }

    public void showMenu() {
        var menu = """
                1 - Search series
                2 - Search episodes
                3 - Show searched series
                4 - Search serie by title
                5 - Search Serie by actor
                6 - Top 5 series
                7 - Search serie by genre
                8 - Filter series
                9 - Search Episode by excerpt
                10 - Top episodes by serie

                0 - Exit
                """;
        var option = -1;

        while (option != 0) {
            System.out.println(menu);
            option = reader.nextInt();
            reader.nextLine();

            switch (option) {
                case 1:
                    searchSerieWeb();
                    break;
                case 2:
                    searchEpisodeFromSerie();
                    break;
                case 3:
                    showSearchedSeries();
                    break;
                case 4:
                    searchSerieByTitle();
                    break;
                case 5:
                    searchSerieByActor();
                    break;
                case 6:
                    searchTop5Series();
                    break;
                case 7:
                    searchSerieByGenre();
                    break;
                case 8:
                    filterByseasonsAndRating();
                    break;
                case 9:
                    searchEpisodeByExcerpt();
                    break;
                case 10:
                    topEpisodesFromSerie();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void searchSerieWeb() {
        SeriesData data = getSeriesData();
        // seriesData.add(data);
        Serie serie = new Serie(data);
        repository.save(serie);
        System.out.println(data);
    }

    private SeriesData getSeriesData() {
        System.out.println("Enter the series name you're looking for: ");
        var seriesName = reader.nextLine();
        var json = useApi.getData(ADDRESS + seriesName.replace(" ", "+") + API_KEY);
        SeriesData data = converter.getData(json, SeriesData.class);
        return data;
    }

    private void searchEpisodeFromSerie() {
        showSearchedSeries();
        System.out.println("Choose a serie by the name");
        var serieName = reader.nextLine();
        Optional<Serie> serie = repository.findByTitleContainingIgnoreCase(serieName);

        if (serie.isPresent()) {
            var foundSerie = serie.get();
            List<SeasonData> seasons = new ArrayList<>();

            for (int i = 1; i <= foundSerie.getSeasons(); i++) {
                var json = useApi.getData(ADDRESS + foundSerie.getTitle().replace(" ", "+") + "&season=" + i + API_KEY);
                SeasonData dadosTemporada = converter.getData(json, SeasonData.class);
                seasons.add(dadosTemporada);
            }
            seasons.forEach(System.out::println);

            List<Episode> episodes = seasons.stream()
                    .flatMap(d -> d.episodes().stream()
                            .map(e -> new Episode(d.number(), e)))
                    .collect(Collectors.toList());

            foundSerie.setEpisodes(episodes);
            repository.save(foundSerie);
        } else {
            System.out.println("Serie not found.");
        }
    }

    private void showSearchedSeries() {
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
    }

    private void searchSerieByTitle() {
        System.out.println("Enter the serie's name: ");
        var serieName = reader.nextLine();
        searchSerie = repository.findByTitleContainingIgnoreCase(serieName);

        if (searchSerie.isPresent()) {
            System.out.println(searchSerie.get());
        } else {
            System.out.println("Serie not found!");
        }
    }

    private void searchSerieByActor() {
        System.out.println("Who are you looking for?");
        var actor = reader.nextLine();

        System.out.println("From what rating?");
        var rating = reader.nextDouble();

        List<Serie> foundedSeries = repository.findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(actor, rating);
        System.out.println("Series that " + actor + " appears: ");
        foundedSeries.forEach(s -> System.out.println(s.getTitle() + " rating: " + s.getRating()));
    }

    private void searchTop5Series() {
        List<Serie> topSeries = repository.findTop5ByOrderByRatingDesc();
        topSeries.forEach(s -> System.out.println(s.getTitle() + " rating: " + s.getRating()));
    }

    private void searchSerieByGenre() {
        System.out.println("Wich genre you want to search: ");
        var genreName = reader.nextLine();
        Genres genre = Genres.fromString(genreName);
        List<Serie> seriesByGenre = repository.findByGenre(genre);
        System.out.println("Series of genre: " + genreName);
        seriesByGenre.forEach(System.out::println);
    }

    private void filterByseasonsAndRating() {
        System.out.println("Filter series up to how many seasons? ");
        var seasons = reader.nextInt();
        reader.nextLine();

        System.out.println("From what rating? ");
        var rating = reader.nextDouble();
        reader.nextLine();

        List<Serie> seriesFiltered = repository.seriesBySeasonAndRating(seasons, rating);
        System.out.println("*** Filtered series ***");
        seriesFiltered.forEach(s -> System.out.println(s.getTitle() + " rating: " + s.getRating()));
    }

    private void searchEpisodeByExcerpt() {
        System.out.println("Enter the episode title: ");
        var excerptEpisode = reader.nextLine();

        List<Episode> foundedEpisodes = repository.episodesByExcerpt(excerptEpisode);
        foundedEpisodes.forEach(e -> System.out.printf("Serie: %s Season %s - Episode %s - %s\n",
                e.getSerie().getTitle(), e.getSeason(),
                e.getNumber(), e.getTitle()));
    }

    private void topEpisodesFromSerie() {
        searchSerieByTitle();

        if (searchSerie.isPresent()) {
            Serie serie = searchSerie.get();
            List<Episode> topEpisodes = repository.topEpisodesBySerie(serie);
            topEpisodes.forEach(e -> System.out.printf("Serie: %s Season %s - Episode %s - %s Rating %s\n",
                    e.getSerie().getTitle(), e.getSeason(),
                    e.getNumber(), e.getTitle(), e.getRating()));
        }
    }
}
