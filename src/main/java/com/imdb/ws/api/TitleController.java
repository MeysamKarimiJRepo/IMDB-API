package com.imdb.ws.api;

import com.imdb.ws.dto.TitleBasicDTO;
import com.imdb.ws.entity.TitleBasic;
import com.imdb.ws.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/titles")
public class TitleController {

    @Autowired
    private TitleService titleService;

    @GetMapping("/director-writer-alive")
    public List<TitleBasicDTO> getTitlesWithSameDirectorAndWriterAlive() {
        return titleService.getTitlesWithSameDirectorAndWriterAlive();
    }

    @GetMapping("/common-titles")
    public List<TitleBasicDTO> getCommonTitles(@RequestParam String actor1, @RequestParam String actor2) {
        return titleService.getCommonTitles(actor1, actor2);
    }

    @GetMapping("/best-titles")
    public List<TitleBasicDTO> getBestTitlesByGenre(@RequestParam String genre) {
        return titleService.getBestTitlesByGenre(genre);
    }


}

