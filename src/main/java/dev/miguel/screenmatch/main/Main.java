package dev.miguel.screenmatch.main;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import dev.miguel.screenmatch.model.Episode;
import dev.miguel.screenmatch.model.EpisodeData;
import dev.miguel.screenmatch.model.SeasonData;
import dev.miguel.screenmatch.model.SeriesData;
import dev.miguel.screenmatch.service.DataConverter;
import dev.miguel.screenmatch.service.UseApi;

public class Main {
	private Scanner read = new Scanner(System.in);

	private UseApi useApi = new UseApi();
	private DataConverter converter = new DataConverter();

	private final String ADDRESS = "https://www.omdbapi.com/?t=";
	private final String API_KEY = "&apikey=6585022c";

	public void showMenu(){
		System.out.println("Enter the series you're looking for: ");
		String seriesName = read.nextLine();
		String json = useApi.getData(ADDRESS + seriesName.replace(" ","+") + API_KEY);
		SeriesData series = converter.getData(json, SeriesData.class);
		System.out.println(series);

		List<SeasonData> seasons = new ArrayList<>();

		for (int i = 1; i<=series.seasons(); i++) {
			json = useApi.getData(ADDRESS + seriesName.replace(" ","+") + "&season=" + i + API_KEY);
			SeasonData season = converter.getData(json, SeasonData.class);
			seasons.add(season);
		}

		seasons.forEach(System.out::println);

		List<EpisodeData> dataEpisodes = seasons.stream()
			.flatMap(t -> t.episodes().stream())
			.collect(Collectors.toList());

		System.out.println("Top 5 episodes:");
		dataEpisodes.stream()
			.filter(e -> !e.rating().equalsIgnoreCase("N/A"))
			.sorted(Comparator.comparing(EpisodeData::rating).reversed())
			.limit(5)
			.forEach(System.out::println);

		List<Episode> episodes = seasons.stream()
			.flatMap(s -> s.episodes().stream()
				.map(d -> new Episode(s.number(), d))
			).collect(Collectors.toList());

		episodes.forEach(System.out::println);

		System.out.println("Wich episode are you looking for? ");
		String title = read.nextLine();
		Optional<Episode> searchedEpisode = episodes.stream()
				.filter(e -> e.getTitle().toUpperCase().contains(title.toUpperCase()))
				.findFirst();

		if (searchedEpisode.isPresent()) {
			System.out.println("Episode found!");
			System.out.println("Season: " + searchedEpisode.get().getSeason());
		} else {
			System.out.println("Episode not found :(");
		}
//		System.out.println("From what year do you want to see the episodes? ");
//
//		int year = read.nextInt();
//		read.nextLine();
//
//		LocalDate searchDate = LocalDate.of(year, 1, 1);
//
//		episodes.stream()
//			.filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(searchDate))
//			.forEach(e -> System.out.println(
//				"Season: " + e.getSeason() +
//				"Episode: " + e.getNumber() +
//				"Release Date:" + e.getReleaseDate()
//			));
	}
}
