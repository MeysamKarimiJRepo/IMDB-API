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
    private TitleBasicsRepository titleBasicsRepository;
    @Autowired
    private TitleAkasRepository titleAkasRepository;
    @Autowired
    private TitleEpisodeRepository titleEpisodeRepository;
    @Autowired
    private TitlePrincipalsRepository titlePrincipalsRepository;
    @Autowired
    private TitleRatingsRepository titleRatingsRepository;
    @Autowired
    private NameBasicsRepository nameBasicsRepository;

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

        // Create TitleBasics entries
        TitleBasics titleBasics1 = new TitleBasics();
        titleBasics1.setTconst("tt0000001");
        titleBasics1.setTitleType("short");
        titleBasics1.setPrimaryTitle("Carmencita");
        titleBasics1.setOriginalTitle("Carmencita");
        titleBasics1.setAdult(false);
        titleBasics1.setStartYear(1894);
        titleBasics1.setRuntimeMinutes(1);
        titleBasics1.setGenres(new HashSet<>(List.of(docGenre, shortGenre)));

        TitleBasics titleBasics2 = new TitleBasics();
        titleBasics2.setTconst("tt0000002");
        titleBasics2.setTitleType("short");
        titleBasics2.setPrimaryTitle("Le clown et ses chiens");
        titleBasics2.setOriginalTitle("Le clown et ses chiens");
        titleBasics2.setAdult(false);
        titleBasics2.setStartYear(1892);
        titleBasics2.setRuntimeMinutes(5);
        titleBasics2.setGenres(new HashSet<>(List.of(docGenre, animGenre)));

        titleBasicsRepository.saveAll(Arrays.asList(titleBasics1, titleBasics2));

        // Create TitleAkas entries
        TitleAkas titleAkas1 = new TitleAkas();
        titleAkas1.setTitleBasics(titleBasics1);
        titleAkas1.setOrdering(1);
        titleAkas1.setTitle("Carmencita");
        titleAkas1.setRegion("US");
        titleAkas1.setTypes(Arrays.asList("imdbDisplay"));
        titleAkas1.setOriginalTitle(true);

        TitleAkas titleAkas2 = new TitleAkas();
        titleAkas2.setTitleBasics(titleBasics2);
        titleAkas2.setOrdering(1);
        titleAkas2.setTitle("Le clown et ses chiens");
        titleAkas2.setRegion("FR");
        titleAkas2.setTypes(Arrays.asList("original"));
        titleAkas2.setOriginalTitle(true);

        titleAkasRepository.saveAll(Arrays.asList(titleAkas1, titleAkas2));

        // Create NameBasics entries
        NameBasics nameBasics1 = new NameBasics();
        nameBasics1.setNconst("nm0000001");
        nameBasics1.setPrimaryName("Fred Astaire");
        nameBasics1.setBirthYear(1899);
        nameBasics1.setPrimaryProfession(Arrays.asList("soundtrack", "actor", "miscellaneous"));
        nameBasics1.setKnownForTitles(Arrays.asList("tt0000001", "tt0000002"));

        NameBasics nameBasics2 = new NameBasics();
        nameBasics2.setNconst("nm0000002");
        nameBasics2.setPrimaryName("Lauren Bacall");
        nameBasics2.setBirthYear(1924);
        nameBasics2.setPrimaryProfession(Arrays.asList("actress", "soundtrack"));
        nameBasics2.setKnownForTitles(Arrays.asList("tt0000001"));

        NameBasics nameBasics3 = new NameBasics();
        nameBasics3.setNconst("nm0000003");
        nameBasics3.setPrimaryName("Alizadeh");
        nameBasics3.setBirthYear(1924);
        nameBasics3.setPrimaryProfession(Arrays.asList("writer", "director"));
        nameBasics3.setKnownForTitles(Arrays.asList("tt0000001"));
        // Ensure nameBasics3 is alive (no deathYear set)

        nameBasicsRepository.saveAll(Arrays.asList(nameBasics1, nameBasics2, nameBasics3));

        // Create TitleEpisode entries
        TitleEpisode titleEpisode1 = new TitleEpisode();
        titleEpisode1.setTconst("tt0000003");
        titleEpisode1.setSeasonNumber(1);
        titleEpisode1.setEpisodeNumber(1);
        titleEpisode1.setParentTitle(titleBasics1);

        TitleEpisode titleEpisode2 = new TitleEpisode();
        titleEpisode2.setTconst("tt0000004");
        titleEpisode2.setSeasonNumber(1);
        titleEpisode2.setEpisodeNumber(2);
        titleEpisode2.setParentTitle(titleBasics2);

        titleEpisodeRepository.saveAll(Arrays.asList(titleEpisode1, titleEpisode2));

        // Create TitlePrincipals entries
        TitlePrincipals titlePrincipals1 = new TitlePrincipals();
        titlePrincipals1.setTitleBasics(titleBasics1);
        titlePrincipals1.setOrdering(1);
        titlePrincipals1.setNameBasics(nameBasics1);
        titlePrincipals1.setCategory(getOrCreateCategory("actor"));

        TitlePrincipals titlePrincipals2 = new TitlePrincipals();
        titlePrincipals2.setTitleBasics(titleBasics2);
        titlePrincipals2.setOrdering(2);
        titlePrincipals2.setNameBasics(nameBasics2);
        titlePrincipals2.setCategory(getOrCreateCategory("director"));

        TitlePrincipals titlePrincipals3 = new TitlePrincipals();
        titlePrincipals3.setTitleBasics(titleBasics1);
        titlePrincipals3.setOrdering(1);
        titlePrincipals3.setNameBasics(nameBasics3);
        titlePrincipals3.setCategory(getOrCreateCategory("writer"));

        TitlePrincipals titlePrincipals4 = new TitlePrincipals();
        titlePrincipals4.setTitleBasics(titleBasics1);
        titlePrincipals4.setOrdering(2);
        titlePrincipals4.setNameBasics(nameBasics3);
        titlePrincipals4.setCategory(getOrCreateCategory("director"));

        titlePrincipalsRepository.saveAll(Arrays.asList(titlePrincipals1, titlePrincipals2, titlePrincipals3, titlePrincipals4));

        // Create TitleRatings entries
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
        Optional<TitleRatings> existingRating = titleRatingsRepository.findByTitleBasicsTconst(tconst);

        if (existingRating.isPresent()) {
            TitleRatings titleRatings = existingRating.get();
            titleRatings.setAverageRating(averageRating);
            titleRatings.setNumVotes(numVotes);
            titleRatingsRepository.save(titleRatings);
        } else {
            TitleRatings newRating = new TitleRatings();
            TitleBasics titleBasics = titleBasicsRepository.getReferenceById(tconst);
            newRating.setTitleBasics(titleBasics);
            newRating.setAverageRating(averageRating);
            newRating.setNumVotes(numVotes);
            titleRatingsRepository.save(newRating);
        }
    }
}
