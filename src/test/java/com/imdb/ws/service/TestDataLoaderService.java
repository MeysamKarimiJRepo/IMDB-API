package com.imdb.ws.service;

import com.imdb.ws.data.*;
import com.imdb.ws.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class TestDataLoaderService {

    public static final String DIRECTOR = "director";
    public static final String ACTOR = "actor";
    public static final String WRITER = "writer";
    @Autowired
    private TitleBasicRepository titleBasicRepository;
    @Autowired
    private TitleAkasRepository titleAkasRepository;
    @Autowired
    private TitleEpisodeRepository titleEpisodeRepository;
    @Autowired
    private TitlePrincipalRepository titlePrincipalRepository;
    @Autowired
    private TitleRatingRepository titleRatingRepository;
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Transactional
    public void loadTestData() {
        Genre docGenre = new Genre();
        docGenre.setName("Documentary");
        genreRepository.save(docGenre);

        Genre shortGenre = new Genre();
        shortGenre.setName("Short");
        genreRepository.save(shortGenre);

        Genre animGenre = new Genre();
        animGenre.setName("Animation");
        genreRepository.save(animGenre);

        // Create TitleBasic entries
        TitleBasic titleBasic1 = new TitleBasic();
        titleBasic1.setTconst("tt0000001");
        titleBasic1.setTitleType("short");
        titleBasic1.setPrimaryTitle("Carmencita");
        titleBasic1.setOriginalTitle("Carmencita");
        titleBasic1.setAdult(false);
        titleBasic1.setStartYear(1894);
        titleBasic1.setRuntimeMinutes(1);
        titleBasic1.setGenres(new HashSet<>(List.of(docGenre, shortGenre)));

        TitleBasic titleBasic2 = new TitleBasic();
        titleBasic2.setTconst("tt0000002");
        titleBasic2.setTitleType("short");
        titleBasic2.setPrimaryTitle("Le clown et ses chiens");
        titleBasic2.setOriginalTitle("Le clown et ses chiens");
        titleBasic2.setAdult(false);
        titleBasic2.setStartYear(1892);
        titleBasic2.setRuntimeMinutes(5);
        titleBasic2.setGenres(new HashSet<>(List.of(docGenre, animGenre)));

        titleBasicRepository.saveAll(Arrays.asList(titleBasic1, titleBasic2));

        // Create TitleAkas entries
        TitleAkas titleAkas1 = new TitleAkas();
        titleAkas1.setTitleBasics(titleBasic1);
        titleAkas1.setOrdering(1);
        titleAkas1.setTitle("Carmencita");
        titleAkas1.setRegion("US");
        titleAkas1.setTypes(Arrays.asList("imdbDisplay"));
        titleAkas1.setOriginalTitle(true);

        TitleAkas titleAkas2 = new TitleAkas();
        titleAkas2.setTitleBasics(titleBasic2);
        titleAkas2.setOrdering(1);
        titleAkas2.setTitle("Le clown et ses chiens");
        titleAkas2.setRegion("FR");
        titleAkas2.setTypes(Arrays.asList("original"));
        titleAkas2.setOriginalTitle(true);

        titleAkasRepository.saveAll(Arrays.asList(titleAkas1, titleAkas2));

        // Create Person entries
        Person person1 = new Person();
        person1.setNconst("nm0000001");
        person1.setPrimaryName("Fred Astaire");
        person1.setBirthYear(1899);
        person1.setPrimaryProfession(Arrays.asList("soundtrack", "actor", "miscellaneous"));
        person1.setKnownForTitles(Arrays.asList("tt0000001", "tt0000002"));

        Person person2 = new Person();
        person2.setNconst("nm0000002");
        person2.setPrimaryName("Lauren Bacall");
        person2.setBirthYear(1924);
        person2.setPrimaryProfession(Arrays.asList("actress", "soundtrack"));
        person2.setKnownForTitles(Arrays.asList("tt0000001"));

        Person person3 = new Person();
        person3.setNconst("nm0000003");
        person3.setPrimaryName("Alizadeh");
        person3.setBirthYear(1924);
        person3.setPrimaryProfession(Arrays.asList("writer", "director"));
        person3.setKnownForTitles(Arrays.asList("tt0000001"));
        // Ensure person3 is alive (no deathYear set)

        personRepository.saveAll(Arrays.asList(person1, person2, person3));

        // Create TitleEpisode entries
        TitleEpisode titleEpisode1 = new TitleEpisode();
        titleEpisode1.setTconst("tt0000003");
        titleEpisode1.setSeasonNumber(1);
        titleEpisode1.setEpisodeNumber(1);
        titleEpisode1.setParentTitle(titleBasic1);

        TitleEpisode titleEpisode2 = new TitleEpisode();
        titleEpisode2.setTconst("tt0000004");
        titleEpisode2.setSeasonNumber(1);
        titleEpisode2.setEpisodeNumber(2);
        titleEpisode2.setParentTitle(titleBasic2);

        titleEpisodeRepository.saveAll(Arrays.asList(titleEpisode1, titleEpisode2));

        // Create TitlePrincipal entries
        TitlePrincipal titlePrincipal1 = new TitlePrincipal();
        titlePrincipal1.setTitleBasics(titleBasic1);
        titlePrincipal1.setOrdering(1);
        titlePrincipal1.setNameBasics(person1);
        titlePrincipal1.setCategory(getOrCreateCategory("actor"));

        TitlePrincipal titlePrincipal2 = new TitlePrincipal();
        titlePrincipal2.setTitleBasics(titleBasic2);
        titlePrincipal2.setOrdering(2);
        titlePrincipal2.setNameBasics(person2);
        titlePrincipal2.setCategory(getOrCreateCategory("director"));

        TitlePrincipal titlePrincipal3 = new TitlePrincipal();
        titlePrincipal3.setTitleBasics(titleBasic1);
        titlePrincipal3.setOrdering(1);
        titlePrincipal3.setNameBasics(person3);
        titlePrincipal3.setCategory(getOrCreateCategory("writer"));

        TitlePrincipal titlePrincipal4 = new TitlePrincipal();
        titlePrincipal4.setTitleBasics(titleBasic1);
        titlePrincipal4.setOrdering(2);
        titlePrincipal4.setNameBasics(person3);
        titlePrincipal4.setCategory(getOrCreateCategory("director"));

        titlePrincipalRepository.saveAll(Arrays.asList(titlePrincipal1, titlePrincipal2, titlePrincipal3, titlePrincipal4));

        // Create TitleRating entries
        createOrUpdateTitleRating("tt0000001", 5.6, 1500);
        createOrUpdateTitleRating("tt0000002", 6.3, 800);
    }


    private Category getOrCreateCategory(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    Category category = new Category();
                    category.setName(categoryName);
                    return categoryRepository.save(category);
                });
    }

    private void createOrUpdateTitleRating(String tconst, double averageRating, int numVotes) {
        Optional<TitleRating> existingRating = titleRatingRepository.findByTitleBasicTconst(tconst);

        if (existingRating.isPresent()) {
            TitleRating titleRating = existingRating.get();
            titleRating.setAverageRating(averageRating);
            titleRating.setNumVotes(numVotes);
            titleRatingRepository.save(titleRating);
        } else {
            TitleRating newRating = new TitleRating();
            TitleBasic titleBasic = titleBasicRepository.getReferenceById(tconst);
            newRating.setTitleBasics(titleBasic);
            newRating.setAverageRating(averageRating);
            newRating.setNumVotes(numVotes);
            titleRatingRepository.save(newRating);
        }
    }
}
