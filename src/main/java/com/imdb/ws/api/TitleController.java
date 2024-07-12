package com.imdb.ws.api;

import com.imdb.ws.dto.TitleBasicDTO;
import com.imdb.ws.service.TitleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/titles")
public class TitleController {
    private static final Logger logger = LoggerFactory.getLogger(TitleController.class);

    @Autowired
    private TitleService titleService;

    @GetMapping("/director-writer-alive")
    public List<TitleBasicDTO> getTitlesWithSameDirectorAndWriterAlive() {
        logger.info("api director-writer-alive is called");
        return titleService.getTitlesWithSameDirectorAndWriterAlive();
    }

    @GetMapping("/common-titles")
    public List<TitleBasicDTO> getCommonTitles(@RequestParam String actor1, @RequestParam String actor2) {
        logger.info("api common-titles is called");
        return titleService.getCommonTitles(actor1, actor2);
    }

    @GetMapping("/best-titles")
    public List<TitleBasicDTO> getBestTitlesByGenre(@RequestParam String genre) {
        logger.info("api best-titles is called");
        return titleService.getBestTitlesByGenre(genre);
    }


}

