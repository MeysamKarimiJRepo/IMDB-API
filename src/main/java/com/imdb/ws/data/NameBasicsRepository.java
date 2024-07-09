package com.imdb.ws.data;

import com.imdb.ws.entity.NameBasics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NameBasicsRepository extends JpaRepository<NameBasics, String> {
}
