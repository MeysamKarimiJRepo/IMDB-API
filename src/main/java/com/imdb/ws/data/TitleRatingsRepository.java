package com.imdb.ws.data;

import com.imdb.ws.entity.TitleRatings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TitleRatingsRepository extends JpaRepository<TitleRatings, Long> {
    Optional<TitleRatings> findByTitleBasicsTconst(String tconst);

}
