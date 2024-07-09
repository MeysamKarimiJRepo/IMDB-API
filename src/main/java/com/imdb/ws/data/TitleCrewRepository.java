package com.imdb.ws.data;

import com.imdb.ws.entity.TitleCrew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleCrewRepository extends JpaRepository<TitleCrew, Long> {
}