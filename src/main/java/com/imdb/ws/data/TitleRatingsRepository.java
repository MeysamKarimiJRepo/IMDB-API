package com.imdb.ws.data;

import com.imdb.ws.entity.TitleRatings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRatingsRepository extends JpaRepository<TitleRatings, Long> {
}
