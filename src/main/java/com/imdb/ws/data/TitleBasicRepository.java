package com.imdb.ws.data;

import com.imdb.ws.entity.TitleBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleBasicRepository extends JpaRepository<TitleBasic, String> {

    @Query("SELECT DISTINCT t FROM TitleBasic t " +
            "JOIN t.principals p1 " +
            "JOIN t.principals p2 " +
            "WHERE p1.category.name = 'director' " +
            "AND p2.category.name = 'writer' " +
            "AND p1.person = p2.person " +
            "AND p1.person.deathYear IS NULL")
    List<TitleBasic> findTitlesWithSameDirectorAndWriterAlive();




    @Query("SELECT t FROM TitleBasic t " +
            "JOIN TitlePrincipal p1 ON t.tconst = p1.titleBasic.tconst " +
            "JOIN TitlePrincipal p2 ON t.tconst = p2.titleBasic.tconst " +
            "WHERE p1.person.nconst = :actor1 " +
            "AND p2.person.nconst = :actor2 " +
            "AND p1.category.name = 'actor' " +
            "AND p2.category.name = 'actor'")
    List<TitleBasic> findCommonTitles(@Param("actor1") String actor1, @Param("actor2") String actor2);


    @Query(value = "SELECT * FROM ( " +
            "SELECT t.*, " +
            "ROW_NUMBER() OVER (PARTITION BY t.start_year ORDER BY r.num_votes DESC, r.average_rating DESC) as row_num " +
            "FROM title_basic t " +
            "JOIN title_genres g ON t.tconst = g.tconst " +
            "JOIN genre genre ON g.genre_id = genre.id " +
            "JOIN title_rating r ON t.tconst = r.tconst " +
            "WHERE genre.name = :genre " +
            ") subquery " +
            "WHERE subquery.row_num = 1 " +
            "ORDER BY subquery.start_year", nativeQuery = true)
    List<TitleBasic> findBestTitlesByGenreAndYear(@Param("genre") String genre);

}
