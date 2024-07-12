package com.imdb.ws.data;

import com.imdb.ws.entity.TitlePrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitlePrincipalRepository extends JpaRepository<TitlePrincipal, Long> {
}
