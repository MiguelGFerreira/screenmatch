package dev.miguel.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
	}

}
