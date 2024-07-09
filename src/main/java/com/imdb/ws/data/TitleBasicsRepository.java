package com.imdb.ws.data;
import com.imdb.ws.entity.TitleBasics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TitleBasicsRepository extends JpaRepository<TitleBasics, String> {
    @Query("SELECT t FROM TitleBasics t JOIN TitleCrew c ON t.tconst = c.titleBasics.tconst " +
            "JOIN NameBasics n ON c.directors = n.nconst AND c.writers = n.nconst " +
            "WHERE n.deathYear IS NULL")
    List<TitleBasics> findTitlesWithSameDirectorAndWriterAlive();

    @Query("SELECT t FROM TitleBasics t JOIN TitlePrincipals p1 ON t.tconst = p1.titleBasics.tconst " +
            "JOIN TitlePrincipals p2 ON t.tconst = p2.titleBasics.tconst " +
            "WHERE p1.nameBasics.nconst = :actor1 AND p2.nameBasics.nconst = :actor2")
    List<TitleBasics> findCommonTitles(String actor1, String actor2);

    @Query("SELECT t FROM TitleBasics t JOIN TitleRatings r ON t.tconst = r.titleBasics.tconst " +
            "WHERE t.genres LIKE %:genre% " +
            "ORDER BY r.numVotes DESC, r.averageRating DESC")
    List<TitleBasics> findBestTitlesByGenre(String genre);
}