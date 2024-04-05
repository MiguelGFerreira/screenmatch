package dev.miguel.screenmatch.model;

import java.util.OptionalDouble;

public class Serie {
	private String title;
	private Integer seasons;
	private Double rating;
	private Genres genre;
	private String actors;
	private String poster;
	private String plot;

	public Serie(SeriesData data) {
		this.title = data.title();
		this.seasons = data.seasons();
		this.rating = OptionalDouble.of(Double.valueOf(data.rating())).orElse(0.0);
		this.genre = Genres.fromString(data.genre().split(",")[0].trim());
		this.actors = data.actors();
		this.poster = data.poster();
		this.plot = data.poster();
	}
	
}
