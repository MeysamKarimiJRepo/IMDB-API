package com.imdb.ws.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "title_rating")
public class TitleRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double averageRating;
    private int numVotes;

    @OneToOne
    @JoinColumn(name = "tconst", nullable = false, unique = true)
    private TitleBasic titleBasic;

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

    public TitleBasic getTitleBasics() {
        return titleBasic;
    }

    public void setTitleBasics(TitleBasic titleBasic) {
        this.titleBasic = titleBasic;
    }
}
