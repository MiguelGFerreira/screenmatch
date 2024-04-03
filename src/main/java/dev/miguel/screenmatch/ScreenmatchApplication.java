package dev.miguel.screenmatch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.miguel.screenmatch.model.EpisodeData;
import dev.miguel.screenmatch.model.SeasonData;
import dev.miguel.screenmatch.model.SeriesData;
import dev.miguel.screenmatch.service.DataConverter;
import dev.miguel.screenmatch.service.UseApi;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UseApi useApi = new UseApi();
		String json = useApi.getData("https://www.omdbapi.com/?t=suits&apikey=6585022c");
		System.out.println(json);

		DataConverter converter = new DataConverter();
		SeriesData data = converter.getData(json, SeriesData.class);
		System.out.println(data);

		json = useApi.getData("https://www.omdbapi.com/?t=suits&season=3&episode=6&apikey=6585022c");
		EpisodeData episode = converter.getData(json, EpisodeData.class);
		System.out.println(episode);
		List<SeasonData> seasons = new ArrayList<>();

		for (int i = 1; i<=data.seasons(); i++) {
			json = useApi.getData("https://www.omdbapi.com/?t=suits&season=" + i + "&apikey=6585022c");
			SeasonData season = converter.getData(json, SeasonData.class);
			seasons.add(season);
		}

		seasons.forEach(System.out::println);
	}

}
