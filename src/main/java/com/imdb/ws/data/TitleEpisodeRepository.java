package com.imdb.ws.data;
import com.imdb.ws.entity.TitleEpisode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleEpisodeRepository extends JpaRepository<TitleEpisode, String> {
}
