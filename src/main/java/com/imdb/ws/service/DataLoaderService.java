package com.imdb.ws.service;

import com.imdb.ws.data.*;
import com.imdb.ws.entity.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.GZIPInputStream;

@Service
public class DataLoaderService {
    private static final Logger logger = LoggerFactory.getLogger(DataLoaderService.class);

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

    @Value("${dataset.folder.path}")
    private String datasetFolderPath;

    @Value("${loadDataFromDataSet}")
    private boolean loadDataFromDataSet;

    @PostConstruct
    @Transactional
    public void loadData() {
        if (loadDataFromDataSet) {
            Instant start = Instant.now();

            try {
                loadTitleBasics();
                loadTitleAkas();
                loadTitleEpisode();
                loadTitlePrincipals();
                loadTitleRatings();
                loadNameBasics();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Instant end = Instant.now();
                Duration duration = Duration.between(start, end);
                logger.info("Data loading completed in {} seconds", duration.getSeconds());
            }
        } else {
            logger.info("The loadDataFromDataSet setting in the properties file set to false, So data will not be loaded from dataset");
        }
    }

    private void loadTitleBasics() throws Exception {
        HashSet<String> genres = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(Paths.get(datasetFolderPath, "title.basics.tsv.gz").toFile()))))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip the header line
                if (line.startsWith("tconst")) {
                    continue;
                }
                String[] fields = line.split("\t");
                TitleBasic titleBasic = new TitleBasic();
                titleBasic.setTconst(fields[0]);
                titleBasic.setTitleType(fields[1]);
                titleBasic.setPrimaryTitle(fields[2]);
                titleBasic.setOriginalTitle(fields[3]);
                titleBasic.setAdult(fields[4].equals("1"));
                titleBasic.setStartYear(fields[5].equals("\\N") ? null : Integer.parseInt(fields[5]));
                titleBasic.setEndYear(fields[6].equals("\\N") ? null : Integer.parseInt(fields[6]));
                titleBasic.setRuntimeMinutes(fields[7].equals("\\N") ? null : Integer.parseInt(fields[7]));
                List<String> genresOfTitle = fields[8].equals("\\N") ? null : List.of(fields[8].split(","));
                Genre genre = null;

                if (genresOfTitle != null) {
                    Set<Genre> genreList = new HashSet<>();
                    for (String genreName : genresOfTitle) {
                        if (!genres.contains(genre)) {
                            genre = new Genre();
                            genre.setName(genreName);
                            genre = genreRepository.save(genre);
                            genres.add(genreName);
                        } else {
                            genre = genreRepository.findByName(genreName);
                        }
                        genreList.add(genre);
                    }
                    titleBasic.setGenres(genreList);
                }


                titleBasicRepository.save(titleBasic);
            }
        }
    }

    private void loadTitleAkas() throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(Paths.get(datasetFolderPath, "title.akas.tsv.gz").toFile()))))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip the header line
                if (line.startsWith("titleId")) {
                    continue;
                }
                String[] fields = line.split("\t");
                TitleAkas titleAkas = new TitleAkas();
                titleAkas.setOrdering(Integer.parseInt(fields[1]));
                titleAkas.setTitle(fields[2]);
                titleAkas.setRegion(fields[3]);
                titleAkas.setLanguage(fields[4]);
                titleAkas.setTypes(fields[5].equals("\\N") ? null : List.of(fields[5].split(",")));
                titleAkas.setAttributes(fields[6].equals("\\N") ? null : List.of(fields[6].split(",")));
                titleAkas.setOriginalTitle(fields[7].equals("1"));
                titleAkas.setTitleBasics(titleBasicRepository.getReferenceById(fields[0]));
                titleAkasRepository.save(titleAkas);
            }
        }
    }

    private void loadTitleEpisode() throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(Paths.get(datasetFolderPath, "title.episode.tsv.gz").toFile()))))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip the header line
                if (line.startsWith("tconst")) {
                    continue;
                }
                String[] fields = line.split("\t");
                TitleEpisode titleEpisode = new TitleEpisode();
                titleEpisode.setTconst(fields[0]);
                titleEpisode.setSeasonNumber(fields[2].equals("\\N") ? null : Integer.parseInt(fields[2]));
                titleEpisode.setEpisodeNumber(fields[3].equals("\\N") ? null : Integer.parseInt(fields[3]));
                titleEpisode.setParentTitle(titleBasicRepository.getReferenceById(fields[1]));
                titleEpisodeRepository.save(titleEpisode);
            }
        }
    }

    private void loadTitlePrincipals() throws Exception {
        HashSet<String> categories = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(Paths.get(datasetFolderPath, "title.principals.tsv.gz").toFile()))))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip the header line
                if (line.startsWith("tconst")) {
                    continue;
                }
                String[] fields = line.split("\t");
                TitlePrincipal titlePrincipal = new TitlePrincipal();
                titlePrincipal.setOrdering(Integer.parseInt(fields[1]));
                titlePrincipal.setTitleBasics(titleBasicRepository.getReferenceById(fields[0]));
                titlePrincipal.setNameBasics(personRepository.getReferenceById(fields[2]));
                String CategoryName = fields[3];
                Category category = null;
                if (!categories.contains(CategoryName)) {
                    category = new Category();
                    category.setName(CategoryName);
                    category = categoryRepository.save(category);
                    categories.add(CategoryName);
                } else {
                    category = getOrCreateCategory(CategoryName);
                }
                titlePrincipal.setCategory(category);
                titlePrincipal.setJob(fields[4].equals("\\N") ? null : fields[4]);
                titlePrincipal.setCharacters(fields[5].equals("\\N") ? null : fields[5]);
                titlePrincipalRepository.save(titlePrincipal);
            }
        }
    }

    private void loadTitleRatings() throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(Paths.get(datasetFolderPath, "title.ratings.tsv.gz").toFile()))))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip the header line
                if (line.startsWith("tconst")) {
                    continue;
                }
                String[] fields = line.split("\t");
                TitleRating titleRating = new TitleRating();
                titleRating.setTitleBasics(titleBasicRepository.getReferenceById(fields[0]));
                titleRating.setAverageRating(Double.parseDouble(fields[1]));
                titleRating.setNumVotes(Integer.parseInt(fields[2]));
                titleRatingRepository.save(titleRating);
            }
        }
    }

    private void loadNameBasics() throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(Paths.get(datasetFolderPath, "name.basics.tsv.gz").toFile()))))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip the header line
                if (line.startsWith("nconst")) {
                    continue;
                }
                String[] fields = line.split("\t");
                Person person = new Person();
                person.setNconst(fields[0]);
                person.setPrimaryName(fields[1]);
                person.setBirthYear(fields[2].equals("\\N") ? null : Integer.parseInt(fields[2]));
                person.setDeathYear(fields[3].equals("\\N") ? null : Integer.parseInt(fields[3]));
                person.setPrimaryProfession(fields[4].equals("\\N") ? null : List.of(fields[4].split(",")));
                person.setKnownForTitles(fields[5].equals("\\N") ? null : List.of(fields[5].split(",")));
                personRepository.save(person);
            }
        }
    }

    private Category getOrCreateCategory(String categoryName) {
        return categoryRepository.findByName(categoryName).orElseGet(() -> {
            Category category = new Category();
            category.setName(categoryName);
            return categoryRepository.save(category);
        });
    }


}
