package com.imdb.ws.data;

import com.imdb.ws.entity.NameBasics;
import com.imdb.ws.entity.TitleBasics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NameBasicsRepository extends JpaRepository<NameBasics, String> {

    @Query("SELECT nb FROM NameBasics nb " +
            "JOIN TitleCrew tc ON nb member of tc.directors OR nb member of tc.writers " +
            "WHERE tc.titleBasics.tconst = :tconst")
    List<NameBasics> findDirectorsAndWritersByTconst(@Param("tconst") String tconst);
}
