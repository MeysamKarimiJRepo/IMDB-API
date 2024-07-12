package com.imdb.ws.data;

import com.imdb.ws.entity.TitleRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TitleRatingRepository extends JpaRepository<TitleRating, Long> {
    Optional<TitleRating> findByTitleBasicTconst(String tconst);

}
