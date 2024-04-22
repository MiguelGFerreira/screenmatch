package dev.miguel.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "episodes")
public class Episode {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer season;
	private String title;
	private Integer number;
	private Double rating;
	private LocalDate releaseDate;
	@ManyToOne
	private Serie serie;

	public Episode(Integer season, EpisodeData episodeData) {
		this.season = season;
		this.title = episodeData.title();
		this.number = episodeData.number();
		
		try {
			this.rating = Double.valueOf(episodeData.rating());
		} catch (NumberFormatException e) {
			this.rating = 0.0;
		}

		try {
			this.releaseDate = LocalDate.parse(episodeData.releaseDate());
		} catch (DateTimeParseException e) {
			this.releaseDate = null;
		}
	}

	public void setSeason(Integer season) {
		this.season = season;
	}

	public Integer getSeason() {
		return season;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getNumber() {
		return number;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Double getRating() {
		return rating;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public String toString() {
		return "season = " + season +
				", title = " + title +
				", episode number= " + number +
				", rating = " + rating + 
				", released date = " + releaseDate;
	}

}
