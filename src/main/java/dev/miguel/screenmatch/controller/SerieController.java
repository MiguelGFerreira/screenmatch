package dev.miguel.screenmatch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.miguel.screenmatch.dto.EpisodeDTO;
import dev.miguel.screenmatch.dto.SerieDTO;
import dev.miguel.screenmatch.service.SerieService;

@RestController
@RequestMapping("/series")
public class SerieController {

	@Autowired
	private SerieService service;

	@GetMapping
	public List<SerieDTO> getSeries() {
		return service.getAllSeries();
	}

	@GetMapping("/top5")
	public List<SerieDTO> getTop5Series() {
		return service.getTop5Series();
	}

	@GetMapping("/releases")
	public List<SerieDTO> getReleases() {
		return service.getReleases();
	}

	@GetMapping("/{id}")
	public SerieDTO getById(@PathVariable Long id) {
		return service.getById(id);
	}

	@GetMapping("/{id}/seasons/all")
	public List<EpisodeDTO> getAllSeasons(@PathVariable Long id) {
		return service.getAllSeasons(id);
	}

	@GetMapping("/{id}/seasons/{num}")
	public List<EpisodeDTO> getSeasonByNumber(@PathVariable Long id, @PathVariable Long num) {
		return service.getSeasonByNumber(id, num);
	}
}
