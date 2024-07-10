package com.imdb.ws.data;

import com.imdb.ws.entity.TitleRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TitleRatingRepository extends JpaRepository<TitleRating, Long> {
    Optional<TitleRating> findByTitleBasicTconst(String tconst);

}
