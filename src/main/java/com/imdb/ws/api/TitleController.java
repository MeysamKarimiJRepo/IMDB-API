package com.imdb.ws.api;

import com.imdb.ws.entity.TitleBasics;
import com.imdb.ws.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/titles")
public class TitleController {

    @Autowired
    private TitleService titleService;

    @GetMapping("/director-writer-alive")
    public List<TitleBasics> getTitlesWithSameDirectorAndWriterAlive() {
        return titleService.getTitlesWithSameDirectorAndWriterAlive();
    }

    @GetMapping("/common-titles")
    public List<TitleBasics> getCommonTitles(@RequestParam String actor1, @RequestParam String actor2) {
        return titleService.getCommonTitles(actor1, actor2);
    }

    @GetMapping("/best-titles")
    public List<TitleBasics> getBestTitlesByGenre(@RequestParam String genre) {
        return titleService.getBestTitlesByGenre(genre);
    }

    @GetMapping("/request-count")
    public long getRequestCount() {
        return titleService.getRequestCount();
    }
}

