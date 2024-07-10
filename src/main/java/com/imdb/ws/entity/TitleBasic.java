package com.imdb.ws.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "title_basic")
public class TitleBasic {

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

    @ManyToMany
    @JoinTable(name = "title_genres",
            joinColumns = @JoinColumn(name = "tconst"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(mappedBy = "titleBasic")
    private List<TitleAkas> akas;

    @OneToMany(mappedBy = "titleBasic")
    private List<TitlePrincipal> principals;

    @OneToMany(mappedBy = "parentTitle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TitleEpisode> episodes = new ArrayList<>();

    @OneToOne(mappedBy = "titleBasic")
    private TitleRating ratings;

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

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public List<TitleAkas> getAkas() {
        return akas;
    }

    public void setAkas(List<TitleAkas> akas) {
        this.akas = akas;
    }

    public List<TitleEpisode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<TitleEpisode> episodes) {
        this.episodes = episodes;
    }

    public List<TitlePrincipal> getPrincipals() {
        return principals;
    }

    public void setPrincipals(List<TitlePrincipal> principals) {
        this.principals = principals;
    }

    public TitleRating getRatings() {
        return ratings;
    }

    public void setRatings(TitleRating ratings) {
        this.ratings = ratings;
    }
}
