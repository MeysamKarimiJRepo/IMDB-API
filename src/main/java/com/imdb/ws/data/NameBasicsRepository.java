package com.imdb.ws.data;

import com.imdb.ws.entity.NameBasics;
import com.imdb.ws.entity.TitleBasics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NameBasicsRepository extends JpaRepository<NameBasics, String> {

    @Query("SELECT nb FROM NameBasics nb " +
            "JOIN TitlePrincipals tp ON tp.nameBasics.nconst = nb.nconst " +
            "WHERE tp.titleBasics.tconst = :tconst " +
            "AND (tp.category.name = 'director' OR tp.category.name = 'writer')")
    List<NameBasics> findDirectorsAndWritersByTconst(@Param("tconst") String tconst);

}
