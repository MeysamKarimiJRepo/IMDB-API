package com.imdb.ws.data;

import com.imdb.ws.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    @Query("SELECT nb FROM Person nb " +
            "JOIN TitlePrincipal tp ON tp.person.nconst = nb.nconst " +
            "WHERE tp.titleBasic.tconst = :tconst " +
            "AND (tp.category.name = 'director' OR tp.category.name = 'writer')")
    List<Person> findDirectorsAndWritersByTconst(@Param("tconst") String tconst);

}
