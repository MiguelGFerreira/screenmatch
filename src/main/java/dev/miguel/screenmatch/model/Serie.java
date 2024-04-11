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
		this.plot = data.plot();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSeasons() {
		return seasons;
	}
	
	public void setSeasons(Integer seasons) {
		this.seasons = seasons;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Genres getGenre() {
		return genre;
	}

	public void setGenre(Genres genre) {
		this.genre = genre;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	@Override
	public String toString() {
		return 	"Genre=" + genre +
				", title=" + title +
				", seasons=" + seasons +
				", rating=" + rating +
				", actors=" + actors +
				", plot=" + plot;
	}
}
