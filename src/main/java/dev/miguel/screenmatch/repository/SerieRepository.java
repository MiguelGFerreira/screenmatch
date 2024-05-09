package dev.miguel.screenmatch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.miguel.screenmatch.model.Episode;
import dev.miguel.screenmatch.model.Genres;
import dev.miguel.screenmatch.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {
	Optional<Serie> findByTitleContainingIgnoreCase(String serieName);

	List<Serie> findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(String actor, Double rating);

	List<Serie> findTop5ByOrderByRatingDesc();

	List<Serie> findByGenre(Genres genre);

	@Query("select s from Serie s WHERE s.seasons <= :seasons and s.rating >= :rating")
	List<Serie> seriesBySeasonAndRating(int seasons, Double rating);

	@Query("select e from Serie s join s.episodes e WHERE e.title ILIKE %:excerptEpisode%")
	List<Episode> episodesByExcerpt(String excerptEpisode);

	@Query("select e from Serie s join s.episodes e WHERE s = :serie ORDER BY e.rating DESC LIMIT 5")
	List<Episode> topEpisodesBySerie(Serie serie);

	@Query("SELECT s FROM Serie s JOIN s.episodes e GROUP BY s ORDER BY MAX(e.releaseDate) DESC LIMIT 5")
    List<Serie> findMostRecentEpisode();
}
