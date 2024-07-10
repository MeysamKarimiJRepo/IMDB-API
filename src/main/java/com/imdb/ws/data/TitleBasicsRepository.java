package com.imdb.ws.data;

import com.imdb.ws.entity.TitleBasics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleBasicsRepository extends JpaRepository<TitleBasics, String> {

    @Query("SELECT t FROM TitleBasics t " +
            "JOIN TitlePrincipals p1 ON t.tconst = p1.titleBasics.tconst " +
            "JOIN TitlePrincipals p2 ON t.tconst = p2.titleBasics.tconst " +
            "JOIN p1.nameBasics n " +
            "JOIN p2.nameBasics n2 " +
            "WHERE p1.category.name = 'director' AND p2.category.name = 'writer' AND n = n2 AND n.deathYear IS NULL")
    List<TitleBasics> findTitlesWithSameDirectorAndWriterAlive();


    @Query("SELECT t FROM TitleBasics t " +
            "JOIN TitlePrincipals p1 ON t.tconst = p1.titleBasics.tconst " +
            "JOIN TitlePrincipals p2 ON t.tconst = p2.titleBasics.tconst " +
            "WHERE p1.nameBasics.nconst = :actor1 " +
            "AND p2.nameBasics.nconst = :actor2 " +
            "AND p1.category.name = 'actor' " +
            "AND p2.category.name = 'actor'")
    List<TitleBasics> findCommonTitles(@Param("actor1") String actor1, @Param("actor2") String actor2);


    @Query("SELECT t FROM TitleBasics t " +
            "JOIN t.genres g " +
            "JOIN TitleRatings r ON t.tconst = r.titleBasics.tconst " +
            "WHERE g.name = :genre " +
            "ORDER BY r.numVotes DESC, r.averageRating DESC")
    List<TitleBasics> findBestTitlesByGenre(@Param("genre") String genre);

}
