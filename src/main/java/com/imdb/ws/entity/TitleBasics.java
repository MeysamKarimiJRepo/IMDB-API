package com.imdb.ws.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * reviewed
 */
@Entity
@Table(name = "title_basics")
public class TitleBasics {

    @Id
    @Column(name = "tconst")
    private String tconst;

    private String titleType;
    @Column(name = "primary_title", length = 512)
    private String primaryTitle;

    @Column(name = "original_title", length = 512)
    private String originalTitle;
    private boolean isAdult;
    private Integer startYear;
    private Integer endYear;
    private Integer runtimeMinutes;

    @ElementCollection
    private List<String> genres;

    @OneToMany(mappedBy = "titleBasics")
    private List<TitleAkas> akas;

    @OneToMany(mappedBy = "titleBasics")
    private List<TitleCrew> crew;

    @OneToMany(mappedBy = "titleBasics")
    private List<TitlePrincipals> principals;

    @OneToMany(mappedBy = "parentTitle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TitleEpisode> episodes;

    @OneToOne(mappedBy = "titleBasics")
    private TitleRatings ratings;

    public String getTconst() {
        return tconst;
    }

    public void setTconst(String tconst) {
        this.tconst = tconst;
    }

    public String getTitleType() {
        return titleType;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

    public String getPrimaryTitle() {
        return primaryTitle;
    }

    public void setPrimaryTitle(String primaryTitle) {
        this.primaryTitle = primaryTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public Integer getRuntimeMinutes() {
        return runtimeMinutes;
    }

    public void setRuntimeMinutes(Integer runtimeMinutes) {
        this.runtimeMinutes = runtimeMinutes;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<TitleAkas> getAkas() {
        return akas;
    }

    public void setAkas(List<TitleAkas> akas) {
        this.akas = akas;
    }

    public List<TitleCrew> getCrew() {
        return crew;
    }

    public void setCrew(List<TitleCrew> crew) {
        this.crew = crew;
    }

    public List<TitleEpisode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<TitleEpisode> episodes) {
        this.episodes = episodes;
    }

    public List<TitlePrincipals> getPrincipals() {
        return principals;
    }

    public void setPrincipals(List<TitlePrincipals> principals) {
        this.principals = principals;
    }

    public TitleRatings getRatings() {
        return ratings;
    }

    public void setRatings(TitleRatings ratings) {
        this.ratings = ratings;
    }
}
