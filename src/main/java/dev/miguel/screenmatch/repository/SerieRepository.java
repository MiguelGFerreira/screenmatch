package dev.miguel.screenmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.miguel.screenmatch.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {
	
}
