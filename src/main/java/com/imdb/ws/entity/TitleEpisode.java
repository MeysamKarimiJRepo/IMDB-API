package com.imdb.ws.entity;

import jakarta.persistence.*;

/**
 * reviewed
 */
@Entity
@Table(name = "title_episode")
public class TitleEpisode {

    @Id
    @Column(name = "tconst")
    private String tconst; // Unique identifier for the episode

    @Column(name = "season_number")
    private Integer seasonNumber;

    @Column(name = "episode_number")
    private Integer episodeNumber;


    @ManyToOne
    @JoinColumn(name = "parent_tconst")
    private TitleBasics parentTitle;

    public String getTconst() {
        return tconst;
    }

    public void setTconst(String tconst) {
        this.tconst = tconst;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public TitleBasics getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(TitleBasics parentTitle) {
        this.parentTitle = parentTitle;
    }
}