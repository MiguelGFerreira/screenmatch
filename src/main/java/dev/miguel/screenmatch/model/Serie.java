package dev.miguel.screenmatch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "series")
public class Serie {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String title;
	private Integer seasons;
	private Double rating;
	@Enumerated(EnumType.STRING)
	private Genres genre;
	private String actors;
	private String poster;
	private String plot;

	@OneToMany(mappedBy = "serie")
	private List<Episode> episodes = new ArrayList<>();

	public Serie() {};

	public Serie(SeriesData data) {
		this.title = data.title();
		this.seasons = data.seasons();
		this.rating = OptionalDouble.of(Double.valueOf(data.rating())).orElse(0.0);
		this.genre = Genres.fromString(data.genre().split(",")[0].trim());
		this.actors = data.actors();
		this.poster = data.poster();
		this.plot = data.plot();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
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
