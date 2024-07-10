package com.imdb.ws.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "title_episode")
public class TitleEpisode {

    @Id
    @Column(name = "tconst")
    private String tconst;

    @Column(name = "season_number")
    private Integer seasonNumber;

    @Column(name = "episode_number")
    private Integer episodeNumber;


    @ManyToOne
    @JoinColumn(name = "parent_tconst")
    private TitleBasic parentTitle;

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

    public TitleBasic getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(TitleBasic parentTitle) {
        this.parentTitle = parentTitle;
    }
}