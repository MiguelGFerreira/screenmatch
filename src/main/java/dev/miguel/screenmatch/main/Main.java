package dev.miguel.screenmatch.main;

import java.util.*;
import java.util.stream.Collectors;

import dev.miguel.screenmatch.model.Episode;
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

    public Main(SerieRepository repository) {
        this.repository = repository;
    }

    public void showMenu() {
        var menu = """
                1 - Search series
                2 - Search episodes
				3 - Show searched series
                
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
		//seriesData.add(data);
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

    private void searchEpisodeFromSerie(){
        showSearchedSeries();
        System.out.println("Choose a serie by the name");
        var serieName = reader.nextLine();
        Optional<Serie> serie = series.stream()
            .filter(s -> s.getTitle().toLowerCase().contains(serieName.toLowerCase()))
            .findFirst();

        if(serie.isPresent()) {
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
}
