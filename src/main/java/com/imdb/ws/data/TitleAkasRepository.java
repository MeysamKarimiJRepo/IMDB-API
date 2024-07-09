package com.imdb.ws.data;
import com.imdb.ws.entity.TitleAkas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleAkasRepository extends JpaRepository<TitleAkas, Long> {
}
