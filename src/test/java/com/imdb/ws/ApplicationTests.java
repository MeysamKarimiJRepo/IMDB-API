package com.imdb.ws;

import com.imdb.ws.data.PersonRepository;
import com.imdb.ws.data.TitleBasicRepository;
import com.imdb.ws.dto.TitleBasicDTO;
import com.imdb.ws.entity.Person;
import com.imdb.ws.entity.TitleBasic;
import com.imdb.ws.service.TestDataLoaderService;
import com.imdb.ws.service.TitleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ApplicationTests {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TitleBasicRepository titleBasicRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private TestDataLoaderService testDataLoaderService;

    @BeforeEach
    public void setup() {
        testDataLoaderService.loadTestData();
    }

    @Test
    public void testFindDirectorsAndWritersOfATitle() {
        List<Person> all = personRepository.findDirectorsAndWritersByTconst("tt0000001");
        Assertions.assertFalse(all.isEmpty());
    }

    @Test
    public void testFindTitlesWithSameDirectorAndWriterAlive() {
        List<TitleBasic> titles = titleBasicRepository.findTitlesWithSameDirectorAndWriterAlive();

        Assertions.assertNotNull(titles);
        Assertions.assertFalse(titles.isEmpty());
    }


    @Test
    public void testFindCommonTitles() {
        String actor1 = "nm0000001";
        String actor2 = "nm0000002";
        List<TitleBasic> titles = titleBasicRepository.findCommonTitles(actor1, actor2);
        Assertions.assertNotNull(titles);
    }

    @Test
    public void testFindBestTitlesByGenre() {
        String genre = "Short";
        List<TitleBasic> titles = titleBasicRepository.findBestTitlesByGenreAndYear(genre);

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
                .andExpect(content().string("7")); // Assuming n requests were made
    }


    @MockBean
    private TitleService titleService;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testGetTitlesWithSameDirectorAndWriterAlive() throws Exception {
        List<TitleBasic> titles = new ArrayList<>();
        TitleBasic title = new TitleBasic();
        title.setTconst("tt1234567");
        title.setTitleType("movie");
        title.setPrimaryTitle("Example Title");
        titles.add(title);

        List<TitleBasicDTO> titleDTOs = titles.stream()
                .map(t -> modelMapper.map(t, TitleBasicDTO.class))
                .collect(Collectors.toList());

        Mockito.when(titleService.getTitlesWithSameDirectorAndWriterAlive()).thenReturn(titleDTOs);

        mockMvc.perform(get("/api/titles/director-writer-alive"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].tconst").value("tt1234567"))
                .andExpect(jsonPath("$[0].titleType").value("movie"))
                .andExpect(jsonPath("$[0].primaryTitle").value("Example Title"));
    }

    @Test
    public void testGetCommonTitles() throws Exception {
        List<TitleBasic> titles = new ArrayList<>();
        TitleBasic title = new TitleBasic();
        title.setTconst("tt1234567");
        title.setTitleType("movie");
        title.setPrimaryTitle("Common Title");
        titles.add(title);

        List<TitleBasicDTO> titleDTOs = titles.stream()
                .map(t -> modelMapper.map(t, TitleBasicDTO.class))
                .collect(Collectors.toList());

        Mockito.when(titleService.getCommonTitles("actor1", "actor2")).thenReturn(titleDTOs);

        mockMvc.perform(get("/api/titles/common-titles")
                        .param("actor1", "actor1")
                        .param("actor2", "actor2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].tconst").value("tt1234567"))
                .andExpect(jsonPath("$[0].titleType").value("movie"))
                .andExpect(jsonPath("$[0].primaryTitle").value("Common Title"));
    }

    @Test
    public void testGetBestTitlesByGenre() throws Exception {
        List<TitleBasic> titles = new ArrayList<>();
        TitleBasic title = new TitleBasic();
        title.setTconst("tt1234567");
        title.setTitleType("movie");
        title.setPrimaryTitle("Best Genre Title");
        titles.add(title);

        List<TitleBasicDTO> titleDTOs = titles.stream()
                .map(t -> modelMapper.map(t, TitleBasicDTO.class))
                .collect(Collectors.toList());

        Mockito.when(titleService.getBestTitlesByGenre("comedy")).thenReturn(titleDTOs);

        mockMvc.perform(get("/api/titles/best-titles")
                        .param("genre", "comedy"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].tconst").value("tt1234567"))
                .andExpect(jsonPath("$[0].titleType").value("movie"))
                .andExpect(jsonPath("$[0].primaryTitle").value("Best Genre Title"));
    }

}
