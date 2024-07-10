package com.imdb.ws.dto;

public class TitleEpisodeDTO {

    private String tconst;
    private Integer seasonNumber;
    private Integer episodeNumber;
    private String parentTitleTconst;

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

    public String getParentTitleTconst() {
        return parentTitleTconst;
    }

    public void setParentTitleTconst(String parentTitleTconst) {
        this.parentTitleTconst = parentTitleTconst;
    }
}

