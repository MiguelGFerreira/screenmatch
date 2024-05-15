package dev.miguel.screenmatch.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.miguel.screenmatch.dto.EpisodeDTO;
import dev.miguel.screenmatch.dto.SerieDTO;
import dev.miguel.screenmatch.model.Serie;
import dev.miguel.screenmatch.repository.SerieRepository;

@Service
public class SerieService {
	@Autowired
	private SerieRepository repository;

	public List<SerieDTO> getAllSeries() {
		return dataConverter(repository.findAll());
	}

	public List<SerieDTO> getTop5Series() {
		return dataConverter(repository.findTop5ByOrderByRatingDesc());

	}

	private List<SerieDTO> dataConverter(List<Serie> series) {
		return series.stream()
				.map(s -> new SerieDTO(s.getId(), s.getTitle(), s.getSeasons(), s.getRating(), s.getGenre(),
						s.getActors(), s.getPoster(), s.getPlot()))
				.collect(Collectors.toList());
	}

	public List<SerieDTO> getReleases() {
		return dataConverter(repository.findMostRecentEpisode());
	}

	public SerieDTO getById(Long id) {
		Optional<Serie> serie = repository.findById(id);

		if (serie.isPresent()) {
			Serie s = serie.get();
			return new SerieDTO(s.getId(), s.getTitle(), s.getSeasons(), s.getRating(), s.getGenre(), s.getActors(),
					s.getPoster(), s.getPlot());
		}

		return null;
	}

	public List<EpisodeDTO> getAllSeasons(Long id) {
		Optional<Serie> serie = repository.findById(id);

		if (serie.isPresent()) {
			Serie s = serie.get();
			return s.getEpisodes().stream()
					.map(e -> new EpisodeDTO(e.getSeason(), e.getNumber(), e.getTitle()))
					.collect(Collectors.toList());
		}

		return null;
	}

	public List<EpisodeDTO> getSeasonByNumber(Long id, Long num) {
		return repository.getEpisodesBySeason(id, num)
				.stream()
				.map(e -> new EpisodeDTO(e.getSeason(), e.getNumber(), e.getTitle()))
				.collect(Collectors.toList());
	}
}
