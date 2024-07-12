package com.imdb.ws.service;

import com.imdb.ws.data.*;
import com.imdb.ws.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

@Service
public class DataLoaderService {
    private static final Logger logger = LoggerFactory.getLogger(DataLoaderService.class);
    public static final String TITLE_BASICS_TSV_GZ = "title.basics.tsv.gz";
    public static final String TITLE_AKAS_TSV_GZ = "title.akas.tsv.gz";
    public static final String TITLE_EPISODE_TSV_GZ = "title.episode.tsv.gz";
    public static final String TITLE_PRINCIPALS_TSV_GZ = "title.principals.tsv.gz";
    public static final String TITLE_RATINGS_TSV_GZ = "title.ratings.tsv.gz";
    public static final String NAME_BASICS_TSV_GZ = "name.basics.tsv.gz";

    // Instance variable for genres set
    private final Set<String> genres = ConcurrentHashMap.newKeySet();
    private final Set<String> categories = ConcurrentHashMap.newKeySet();

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

    @Autowired
    private FileConfiguration fileConfig;


    @Value("${dataset.folder.path}")
    private String datasetFolderPath;

    @Value("${loadDataFromDataSet}")
    private boolean loadDataFromDataSet;

    @Value("${numThreads:4}")
    private int numThreads;

    @Value("${batchSize:1000}")
    private int batchSize;

    public void loadData() {
        if (loadDataFromDataSet) {
            loadGenresAndCategoriesFromDatabase();
            List<String> files = Collections.unmodifiableList(fileConfig.getFileList());
            Instant start = Instant.now();
            try {
                for (String fileName : files) {
                    loadFileWithMultipleThreads(fileName, numThreads);
                }
            } catch (Exception e) {
                logger.error("In loading data into the database: ", e);
            } finally {
                Instant end = Instant.now();
                Duration duration = Duration.between(start, end);
                logger.info("Data loading completed in {} seconds", duration.getSeconds());
            }
        } else {
            logger.info("The loadDataFromDataSet setting in the properties file set to false, So data will not be loaded from dataset");
        }
    }

    private void loadGenresAndCategoriesFromDatabase() {
        List<Category> categoryList = categoryRepository.findAll();
        for (Category cat : categoryList) {
            categories.add(cat.getName());
        }

        List<Genre> genreList = genreRepository.findAll();
        for (Genre genre : genreList) {
            genres.add(genre.getName());
        }
    }

    public void loadFileWithMultipleThreads(String fileName, int numThreads) throws IOException {
        long totalLines = countLines(fileName);
        long linesPerThread = totalLines / numThreads;
        logger.info(String.format("For %s and total %d lines, %d lines assigned to each thread (%d threads)", fileName, totalLines, linesPerThread, numThreads));
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < numThreads; i++) {
            long startLine = i * linesPerThread + 2; // Skip header
            long endLine = (i == numThreads - 1) ? totalLines + 1 : (i + 1) * linesPerThread + 1;

            executor.submit(() -> {
                try {
                    switch (fileName) {
                        case TITLE_BASICS_TSV_GZ -> loadTitleBasicsInRange(fileName, totalLines, startLine, endLine);
                        case TITLE_AKAS_TSV_GZ -> loadTitleAkasInRange(fileName, totalLines, startLine, endLine);
                        case TITLE_EPISODE_TSV_GZ -> loadTitleEpisodeInRange(fileName, totalLines, startLine, endLine);
                        case TITLE_PRINCIPALS_TSV_GZ ->
                                loadTitlePrincipalsInRange(fileName, totalLines, startLine, endLine);
                        case TITLE_RATINGS_TSV_GZ -> loadTitleRatingsInRange(fileName, totalLines, startLine, endLine);
                        case NAME_BASICS_TSV_GZ -> loadNameBasicsInRange(fileName, totalLines, startLine, endLine);
                        default -> throw new RuntimeException("The file is not supported: " + fileName);
                    }
                } catch (Exception e) {
                    logger.error("Error in processing file: " + fileName + " from line " + startLine + " to " + endLine, e);
                    throw new RuntimeException(e);
                }
            });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(1440, TimeUnit.MINUTES)) {
                logger.warn("Executor did not terminate in the specified time. Attempting to shut down now.");
                executor.shutdownNow();
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    logger.error("Executor did not terminate.");
                }
            }
        } catch (InterruptedException e) {
            logger.error("Thread interrupted while waiting for executor to terminate.", e);
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }


    @Transactional
    public void loadTitleBasicsInRange(String fileName, long totalLines, long startLine, long endLine) throws Exception {

        long currentLine = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(Paths.get(datasetFolderPath, fileName).toFile()))))) {
            String line;
            List<TitleBasic> titleBasicsBatch = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                try {
                    currentLine++;
                    // Skip the header line
                    if (line.startsWith("tconst")) {
                        continue;
                    }
                    if (currentLine < startLine) continue;
                    if (currentLine > endLine) {
                        printLog(fileName, startLine, endLine);
                        break;
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
                            synchronized (genres) {
                                if (!genres.contains(genreName)) {
                                    genre = new Genre();
                                    genre.setName(genreName);
                                    genre = genreRepository.save(genre);
                                    genres.add(genreName);
                                } else {
                                    genre = genreRepository.findByName(genreName);
                                }
                            }
                            genreList.add(genre);
                        }
                        titleBasic.setGenres(genreList);
                    }

                    titleBasicsBatch.add(titleBasic);
                    if (titleBasicsBatch.size() >= batchSize) {
                        titleBasicRepository.saveAll(titleBasicsBatch);
                        titleBasicsBatch.clear();
                    }


                    printTheProgressOfLoading(currentLine, startLine, totalLines, fileName);
                } catch (Exception e) {
                    printLineCausedError(e.toString(), fileName, line);
                    throw new RuntimeException(e);
                }
            }
            if (!titleBasicsBatch.isEmpty()) {
                titleBasicRepository.saveAll(titleBasicsBatch);
            }
        }
    }

    private void printTheProgressOfLoading(long currentLine, long startLine, long endLine, String fileName) {
        String format = "%s %s Progress: %.2f%%";
        if (currentLine % batchSize == 0 || currentLine == endLine - startLine + 1) {
            double progress = ((double) (currentLine - startLine + 1) / (endLine - startLine + 1)) * 100.0;
            logger.info(String.format(format, Thread.currentThread().getName(), fileName, progress));
        }
    }


    @Transactional
    public void loadTitleAkasInRange(String fileName, long totalLines, long startLine, long endLine) throws Exception {
        long currentLine = 0;
        List<TitleAkas> titleAkasBatch = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(Paths.get(datasetFolderPath, fileName).toFile()))))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    currentLine++;
                    // Skip the header line
                    if (line.startsWith("titleId")) {
                        continue;
                    }
                    if (currentLine < startLine) {
                        continue;
                    }
                    if (currentLine > endLine) {
                        printLog(fileName, startLine, endLine);
                        break;
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
                    titleAkasBatch.add(titleAkas);

                    if (titleAkasBatch.size() >= batchSize) {
                        titleAkasRepository.saveAll(titleAkasBatch);
                        titleAkasBatch.clear();
                    }

                    printTheProgressOfLoading(currentLine, startLine, totalLines, fileName);
                } catch (Exception e) {
                    printLineCausedError(e.toString(), fileName, line);
                    throw new RuntimeException(e);
                }
            }
            if (!titleAkasBatch.isEmpty()) {
                titleAkasRepository.saveAll(titleAkasBatch);
            }
        }
    }

    private static void printLog(String fileName, long startLine, long endLine) {
        logger.info(String.format("%s processed %d to %d of file %s", Thread.currentThread().getName(), startLine, endLine, fileName));
    }

    @Transactional
    public void loadTitleEpisodeInRange(String fileName, long totalLines, long startLine, long endLine) throws Exception {
        long currentLine = 0;
        List<TitleEpisode> titleEpisodeBatch = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(Paths.get(datasetFolderPath, fileName).toFile()))))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    currentLine++;
                    // Skip the header line
                    if (line.startsWith("tconst")) {
                        continue;
                    }

                    if (currentLine < startLine) {
                        continue;
                    }
                    if (currentLine > endLine) {
                        printLog(fileName, startLine, endLine);
                        break;
                    }
                    String[] fields = line.split("\t");
                    TitleEpisode titleEpisode = new TitleEpisode();
                    titleEpisode.setTconst(fields[0]);
                    titleEpisode.setSeasonNumber(fields[2].equals("\\N") ? null : Integer.parseInt(fields[2]));
                    titleEpisode.setEpisodeNumber(fields[3].equals("\\N") ? null : Integer.parseInt(fields[3]));
                    titleEpisode.setParentTitle(titleBasicRepository.getReferenceById(fields[1]));
                    titleEpisodeBatch.add(titleEpisode);

                    if (titleEpisodeBatch.size() >= batchSize) {
                        titleEpisodeRepository.saveAll(titleEpisodeBatch);
                        titleEpisodeBatch.clear();
                    }

                    printTheProgressOfLoading(currentLine, startLine, totalLines, fileName);
                } catch (Exception e) {
                    printLineCausedError(e.toString(), fileName, line);
                    throw new RuntimeException(e);
                }
            }
            if (!titleEpisodeBatch.isEmpty()) {
                titleEpisodeRepository.saveAll(titleEpisodeBatch);
            }
        }
    }

    @Transactional
    public void loadTitlePrincipalsInRange(String fileName, long totalLines, long startLine, long endLine) throws Exception {
        List<TitlePrincipal> titlePrincipalBatch = new ArrayList<>();
        long currentLine = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(Paths.get(datasetFolderPath, fileName).toFile()))))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    currentLine++;
                    // Skip the header line
                    if (line.startsWith("tconst")) {
                        continue;
                    }
                    if (currentLine < startLine) {
                        continue;
                    }
                    if (currentLine > endLine) {
                        printLog(fileName, startLine, endLine);
                        break;
                    }
                    String[] fields = line.split("\t");
                    TitlePrincipal titlePrincipal = new TitlePrincipal();
                    titlePrincipal.setOrdering(Integer.parseInt(fields[1]));
                    titlePrincipal.setTitleBasics(titleBasicRepository.getReferenceById(fields[0]));
                    titlePrincipal.setNameBasics(personRepository.getReferenceById(fields[2]));
                    String CategoryName = fields[3];
                    Category category = null;
                    synchronized (categories) {
                        if (!categories.contains(CategoryName)) {
                            category = new Category();
                            category.setName(CategoryName);
                            category = categoryRepository.save(category);
                            categories.add(CategoryName);
                        } else {
                            category = getOrCreateCategory(CategoryName);
                        }
                    }
                    titlePrincipal.setCategory(category);
                    titlePrincipal.setJob(fields[4].equals("\\N") ? null : fields[4]);
                    titlePrincipal.setCharacters(fields[5].equals("\\N") ? null : fields[5]);
                    titlePrincipalBatch.add(titlePrincipal);

                    if (titlePrincipalBatch.size() >= batchSize) {
                        titlePrincipalRepository.saveAll(titlePrincipalBatch);
                        titlePrincipalBatch.clear();
                    }

                    printTheProgressOfLoading(currentLine, startLine, totalLines, fileName);
                } catch (Exception e) {
                    printLineCausedError(e.toString(), fileName, line);
                    throw new RuntimeException(e);
                }
            }
            if (!titlePrincipalBatch.isEmpty()) {
                titlePrincipalRepository.saveAll(titlePrincipalBatch);
            }
        }
    }

    @Transactional
    public void loadTitleRatingsInRange(String fileName, long totalLines, long startLine, long endLine) throws Exception {
        List<TitleRating> titleRatingBatch = new ArrayList<>();
        long currentLine = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(Paths.get(datasetFolderPath, fileName).toFile()))))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    currentLine++;
                    // Skip the header line
                    if (line.startsWith("tconst")) {
                        continue;
                    }
                    if (currentLine < startLine) {
                        continue;
                    }
                    if (currentLine > endLine) {
                        printLog(fileName, startLine, endLine);
                        break;
                    }
                    String[] fields = line.split("\t");
                    TitleRating titleRating = new TitleRating();
                    titleRating.setTitleBasics(titleBasicRepository.getReferenceById(fields[0]));
                    titleRating.setAverageRating(Double.parseDouble(fields[1]));
                    titleRating.setNumVotes(Integer.parseInt(fields[2]));
                    titleRatingBatch.add(titleRating);

                    if (titleRatingBatch.size() >= batchSize) {
                        titleRatingRepository.saveAll(titleRatingBatch);
                        titleRatingBatch.clear();
                    }


                    printTheProgressOfLoading(currentLine, startLine, totalLines, fileName);
                } catch (Exception e) {
                    printLineCausedError(e.toString(), fileName, line);
                    throw new RuntimeException(e);
                }
            }
            if (!titleRatingBatch.isEmpty()) {
                titleRatingRepository.saveAll(titleRatingBatch);
            }
        }
    }

    @Transactional
    public void loadNameBasicsInRange(String fileName, long totalLines, long startLine, long endLine) throws Exception {
        List<Person> personBatch = new ArrayList<>();
        long currentLine = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(Paths.get(datasetFolderPath, fileName).toFile()))))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    currentLine++;
                    // Skip the header line
                    if (line.startsWith("nconst")) {
                        continue;
                    }
                    if (currentLine < startLine) {
                        continue;
                    }
                    if (currentLine > endLine) {
                        printLog(fileName, startLine, endLine);
                        break;
                    }
                    String[] fields = line.split("\t");
                    Person person = new Person();
                    person.setNconst(fields[0]);
                    person.setPrimaryName(fields[1]);
                    person.setBirthYear(fields[2].equals("\\N") ? null : Integer.parseInt(fields[2]));
                    person.setDeathYear(fields[3].equals("\\N") ? null : Integer.parseInt(fields[3]));
                    person.setPrimaryProfession(fields[4].equals("\\N") ? null : List.of(fields[4].split(",")));
                    person.setKnownForTitles(fields[5].equals("\\N") ? null : List.of(fields[5].split(",")));
                    personBatch.add(person);

                    if (personBatch.size() >= batchSize) {
                        personRepository.saveAll(personBatch);
                        personBatch.clear();
                    }

                    printTheProgressOfLoading(currentLine, startLine, totalLines, fileName);
                } catch (Exception e) {
                    printLineCausedError(e.toString(), fileName, line);
                    throw new RuntimeException(e);
                }
            }
            if (!personBatch.isEmpty()) {
                personRepository.saveAll(personBatch);
            }
        }
    }

    private static void printLineCausedError(String error, String fileName, String line) {
        logger.error(String.format("%s on loading data from %s line : %s", error, fileName, line));
    }

    @Transactional
    public Category getOrCreateCategory(String categoryName) {
        return categoryRepository.findByName(categoryName).orElseGet(() -> {
            Category category = new Category();
            category.setName(categoryName);
            return categoryRepository.save(category);
        });
    }

    public long countLines(String fileName) throws IOException {
        long lineCount = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(Paths.get(datasetFolderPath, fileName).toFile()))))) {
            // the first line is the header and we want to skip it
            br.readLine();


            while (br.readLine() != null) {
                lineCount++;
            }
        }

        return lineCount;
    }

}
