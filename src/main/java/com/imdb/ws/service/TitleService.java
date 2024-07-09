package com.imdb.ws.service;

import com.imdb.ws.data.NameBasicsRepository;
import com.imdb.ws.data.TitleBasicsRepository;
import com.imdb.ws.entity.TitleBasics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleService {

    @Autowired
    private TitleBasicsRepository titleRepository;

    @Autowired
    private NameBasicsRepository nameBasicsRepository;

    @Autowired
    private RequestCountService requestCountService;

    public List<TitleBasics> getTitlesWithSameDirectorAndWriterAlive() {
        return titleRepository.findTitlesWithSameDirectorAndWriterAlive();
    }

    public List<TitleBasics> getCommonTitles(String actor1, String actor2) {
        return titleRepository.findCommonTitles(actor1, actor2);
    }

    public List<TitleBasics> getBestTitlesByGenre(String genre) {
        return titleRepository.findBestTitlesByGenre(genre);
    }

    public long getRequestCount() {
        return requestCountService.getRequestCount();
    }
}
