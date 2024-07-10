package com.imdb.ws;

import com.imdb.ws.data.NameBasicsRepository;
import com.imdb.ws.data.TitleBasicsRepository;
import com.imdb.ws.entity.NameBasics;
import com.imdb.ws.entity.TitleBasics;
import com.imdb.ws.service.TestDataLoaderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ApplicationTests {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TitleBasicsRepository titleBasicsRepository;
    @Autowired
    private NameBasicsRepository nameBasicsRepository;
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
        List<TitleBasics> titles = titleBasicsRepository.findTitlesWithSameDirectorAndWriterAlive();

        Assertions.assertNotNull(titles);
        Assertions.assertFalse(titles.isEmpty());
    }


    @Test
    public void testFindCommonTitles() {
        String actor1 = "nm0000001";
        String actor2 = "nm0000002";
        List<TitleBasics> titles = titleBasicsRepository.findCommonTitles(actor1, actor2);
        Assertions.assertNotNull(titles);
    }

    @Test
    public void testFindBestTitlesByGenre() {
        String genre = "Short";
        List<TitleBasics> titles = titleBasicsRepository.findBestTitlesByGenre(genre);

        assertThat(titles).isNotEmpty();
        titles.forEach(title -> System.out.println(title.getPrimaryTitle()));
    }

    @Test
    public void testFindTitlesWithSameDirectorAndWriterAliveAPI() throws Exception {
        mockMvc.perform(get("/api/titles/director-writer-alive"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindCommonTitlesAPI() throws Exception {
        String actor1 = "nm0000001";
        String actor2 = "nm0000002";
        mockMvc.perform(get("/api/titles/common-titles")
                        .param("actor1", actor1)
                        .param("actor2", actor2))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindBestTitlesByGenreAPI() throws Exception {
        String genre = "Short";
        mockMvc.perform(get("/api/titles/best-titles")
                        .param("genre", genre))
                .andExpect(status().isOk());
    }

    @Test
    public void testCounterWebService() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/metrics/request-count"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("3")); // Assuming 3 requests were made
    }
}
