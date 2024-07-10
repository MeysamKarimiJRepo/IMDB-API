package com.imdb.ws.dto;

import java.util.List;
import java.util.Set;

public class TitleBasicDTO {

    private String tconst;
    private String titleType;
    private String primaryTitle;
    private String originalTitle;
    private boolean adult;
    private Integer startYear;
    private Integer endYear;
    private Integer runtimeMinutes;
    private Set<String> genres;
    private List<TitleAkasDTO> akas;
    private List<TitlePrincipalDTO> principals;
    private List<TitleEpisodeDTO> episodes;
    private TitleRatingDTO ratings;

    // Constructors, getters, and setters

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
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
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

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public List<TitleAkasDTO> getAkas() {
        return akas;
    }

    public void setAkas(List<TitleAkasDTO> akas) {
        this.akas = akas;
    }

    public List<TitlePrincipalDTO> getPrincipals() {
        return principals;
    }

    public void setPrincipals(List<TitlePrincipalDTO> principals) {
        this.principals = principals;
    }

    public List<TitleEpisodeDTO> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<TitleEpisodeDTO> episodes) {
        this.episodes = episodes;
    }

    public TitleRatingDTO getRatings() {
        return ratings;
    }

    public void setRatings(TitleRatingDTO ratings) {
        this.ratings = ratings;
    }
}
