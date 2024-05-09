package dev.miguel.screenmatch.dto;

import dev.miguel.screenmatch.model.Genres;

public record SerieDTO(Long id,
		String title,
		Integer seasons,
		Double rating,
		Genres genre,
		String actors,
		String poster,
		String plot) {

}
