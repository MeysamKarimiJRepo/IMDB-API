package com.imdb.ws.data;

import com.imdb.ws.entity.TitlePrincipals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitlePrincipalsRepository extends JpaRepository<TitlePrincipals, Long> {
}
