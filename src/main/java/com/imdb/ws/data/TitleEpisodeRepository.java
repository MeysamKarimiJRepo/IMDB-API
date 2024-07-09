package com.imdb.ws.data;
import com.imdb.ws.entity.TitleEpisode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleEpisodeRepository extends JpaRepository<TitleEpisode, String> {
}
