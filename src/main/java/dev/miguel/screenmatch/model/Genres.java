package dev.miguel.screenmatch.model;

public enum Genres {
	ACTION("Action"),
	ROMANCE("Romance"),
	COMEDY("Comedy"),
	DRAMA("Drama"),
	CRIME("Crime");

	private String omdbGenre;

	Genres(String omdbGenre) {
		this.omdbGenre = omdbGenre;
	}

	public static Genres fromString(String text) {
		for (Genres genre : Genres.values()) {
			if (genre.omdbGenre.equalsIgnoreCase(text)) {
				return genre;
			}
		}
		throw new IllegalArgumentException("No genres found for the given string: " + text);
	}
}
