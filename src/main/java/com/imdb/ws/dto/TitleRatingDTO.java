package com.imdb.ws.dto;

public class TitleRatingDTO {

    private Long id;
    private double averageRating;
    private int numVotes;
    private String titleBasicTconst;

    public TitleRatingDTO() {
    }

    public TitleRatingDTO(Long id, double averageRating, int numVotes, String titleBasicTconst) {
        this.id = id;
        this.averageRating = averageRating;
        this.numVotes = numVotes;
        this.titleBasicTconst = titleBasicTconst;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(int numVotes) {
        this.numVotes = numVotes;
    }

    public String getTitleBasicTconst() {
        return titleBasicTconst;
    }

    public void setTitleBasicTconst(String titleBasicTconst) {
        this.titleBasicTconst = titleBasicTconst;
    }
}
