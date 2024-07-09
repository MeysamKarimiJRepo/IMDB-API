package com.imdb.ws.service;

import com.imdb.ws.data.*;
import com.imdb.ws.entity.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class DataLoaderService {

    @Autowired
    private TitleBasicsRepository titleBasicsRepository;

    @Autowired
    private TitleAkasRepository titleAkasRepository;

    @Autowired
    private TitleCrewRepository titleCrewRepository;

    @Autowired
    private TitleEpisodeRepository titleEpisodeRepository;

    @Autowired
    private TitlePrincipalsRepository titlePrincipalsRepository;

    @Autowired
    private TitleRatingsRepository titleRatingsRepository;

    @Autowired
    private NameBasicsRepository nameBasicsRepository;

    @Value("${dataset.folder.path}")
    private String datasetFolderPath;

    @PostConstruct
    @Transactional
    public void loadData() {
        try {
            loadTitleBasics();
            loadTitleAkas();
            loadTitleCrew();
            loadTitleEpisode();
            loadTitlePrincipals();
            loadTitleRatings();
            loadNameBasics();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTitleBasics() throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(Paths.get(datasetFolderPath, "title.basics.tsv.gz").toFile()))))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip the header line
                if (line.startsWith("tconst")) {
                    continue;
                }
                String[] fields = line.split("\t");
                TitleBasics titleBasics = new TitleBasics();
                titleBasics.setTconst(fields[0]);
                titleBasics.setTitleType(fields[1]);
                titleBasics.setPrimaryTitle(fields[2]);
                titleBasics.setOriginalTitle(fields[3]);
                titleBasics.setAdult(fields[4].equals("1"));
                titleBasics.setStartYear(fields[5].equals("\\N") ? null : Integer.parseInt(fields[5]));
                titleBasics.setEndYear(fields[6].equals("\\N") ? null : Integer.parseInt(fields[6]));
                titleBasics.setRuntimeMinutes(fields[7].equals("\\N") ? null : Integer.parseInt(fields[7]));
                titleBasics.setGenres(fields[8].equals("\\N") ? null : List.of(fields[8].split(",")));
                titleBasicsRepository.save(titleBasics);
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
                titleAkas.setTitleBasics(titleBasicsRepository.getReferenceById(fields[0]));
                titleAkasRepository.save(titleAkas);
            }
        }
    }

    private void loadTitleCrew() throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(Paths.get(datasetFolderPath, "title.crew.tsv.gz").toFile()))))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip the header line
                if (line.startsWith("tconst")) {
                    continue;
                }
                String[] fields = line.split("\t");
                TitleCrew titleCrew = new TitleCrew();
                titleCrew.setTitleBasics(titleBasicsRepository.getReferenceById(fields[0]));
                titleCrew.setDirectors(fields[1].equals("\\N") ? null : nameBasicsRepository.findAllById(List.of(fields[1].split(","))));
                titleCrew.setWriters(fields[2].equals("\\N") ? null : nameBasicsRepository.findAllById(List.of(fields[2].split(","))));
                titleCrewRepository.save(titleCrew);
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
                titleEpisode.setParentTitle(titleBasicsRepository.getReferenceById(fields[1]));
                titleEpisodeRepository.save(titleEpisode);
            }
        }
    }

    private void loadTitlePrincipals() throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(Paths.get(datasetFolderPath, "title.principals.tsv.gz").toFile()))))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip the header line
                if (line.startsWith("tconst")) {
                    continue;
                }
                String[] fields = line.split("\t");
                TitlePrincipals titlePrincipals = new TitlePrincipals();
                titlePrincipals.setOrdering(Integer.parseInt(fields[1]));
                titlePrincipals.setTitleBasics(titleBasicsRepository.getReferenceById(fields[0]));
                titlePrincipals.setNameBasics(nameBasicsRepository.getReferenceById(fields[2]));
                titlePrincipals.setCategory(fields[3]);
                titlePrincipals.setJob(fields[4].equals("\\N") ? null : fields[4]);
                titlePrincipals.setCharacters(fields[5].equals("\\N") ? null : fields[5]);
                titlePrincipalsRepository.save(titlePrincipals);
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
                TitleRatings titleRatings = new TitleRatings();
                titleRatings.setTitleBasics(titleBasicsRepository.getReferenceById(fields[0]));
                titleRatings.setAverageRating(Double.parseDouble(fields[1]));
                titleRatings.setNumVotes(Integer.parseInt(fields[2]));
                titleRatingsRepository.save(titleRatings);
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
                NameBasics nameBasics = new NameBasics();
                nameBasics.setNconst(fields[0]);
                nameBasics.setPrimaryName(fields[1]);
                nameBasics.setBirthYear(fields[2].equals("\\N") ? null : Integer.parseInt(fields[2]));
                nameBasics.setDeathYear(fields[3].equals("\\N") ? null : Integer.parseInt(fields[3]));
                nameBasics.setPrimaryProfession(fields[4].equals("\\N") ? null : List.of(fields[4].split(",")));
                nameBasics.setKnownForTitles(fields[5].equals("\\N") ? null : List.of(fields[5].split(",")));
                nameBasicsRepository.save(nameBasics);
            }
        }
    }
}
