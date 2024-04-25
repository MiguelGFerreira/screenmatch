package dev.miguel.screenmatch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.miguel.screenmatch.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {
	Optional<Serie> findByTitleContainingIgnoreCase(String serieName);

	List<Serie> findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(String actor, Double rating);
}
