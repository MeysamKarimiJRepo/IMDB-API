package com.imdb.ws;

import com.imdb.ws.data.NameBasicsRepository;
import com.imdb.ws.data.TitleBasicsRepository;
import com.imdb.ws.data.TitleCrewRepository;
import com.imdb.ws.entity.NameBasics;
import com.imdb.ws.entity.TitleBasics;
import com.imdb.ws.entity.TitleCrew;
import com.imdb.ws.service.TestDataLoaderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ApplicationTests {

    @Autowired
    private TitleBasicsRepository titleBasicsRepository;
    @Autowired
    private NameBasicsRepository nameBasicsRepository;
    @Autowired
    private TitleCrewRepository titleCrewRepository;

    @Autowired
    private TestDataLoaderService testDataLoaderService;

    @BeforeEach
    public void setup() {
        testDataLoaderService.loadTestData();
    }

    @Test
    public void testFindAll() {
        List<NameBasics> all = nameBasicsRepository.findDirectorsAndWritersByTconst("tt0000001");
        Assertions.assertFalse(all.isEmpty());
    }

    @Test
    public void testFindTitlesWithSameDirectorAndWriterAlive() {
        List<TitleCrew> all = titleCrewRepository.findAll();

        List<TitleBasics> titles = titleBasicsRepository.findTitlesWithSameDirectorAndWriterAlive();
        // Assertions or checks on the retrieved titles
        Assertions.assertNotNull(titles);
        Assertions.assertFalse(titles.isEmpty());
        // Add more assertions as needed
    }

    //    @Test
    public void testFindCommonTitles() {
        String actor1 = "nm0000001";
        String actor2 = "nm0000002";
        List<TitleBasics> titles = titleBasicsRepository.findCommonTitles(actor1, actor2);
        // Assertions or checks on the retrieved titles
        Assertions.assertNotNull(titles);
        // Add more assertions as needed
    }

    @Test
    public void testFindBestTitlesByGenre() {
        String genre = "Short";
        List<TitleBasics> titles = titleBasicsRepository.findBestTitlesByGenre(genre);

        assertThat(titles).isNotEmpty();
        titles.forEach(title -> System.out.println(title.getPrimaryTitle()));
    }
}
