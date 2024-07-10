package com.imdb.ws.entity;

import jakarta.persistence.*;

import java.util.List;


//@Entity
//@Table(name = "title_crew")
public class TitleCrew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for the entity

    @ManyToOne
    @JoinColumn(name = "tconst", nullable = false)
    private TitleBasics titleBasics;

    @ManyToMany
    @JoinTable(
            name = "title_crew_directors",
            joinColumns = @JoinColumn(name = "crew_id"),
            inverseJoinColumns = @JoinColumn(name = "nconst")
    )
    private List<NameBasics> directors;

    @ManyToMany
    @JoinTable(
            name = "title_crew_writers",
            joinColumns = @JoinColumn(name = "crew_id"),
            inverseJoinColumns = @JoinColumn(name = "nconst")
    )
    private List<NameBasics> writers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TitleBasics getTitleBasics() {
        return titleBasics;
    }

    public void setTitleBasics(TitleBasics titleBasics) {
        this.titleBasics = titleBasics;
    }

    public List<NameBasics> getDirectors() {
        return directors;
    }

    public void setDirectors(List<NameBasics> directors) {
        this.directors = directors;
    }

    public List<NameBasics> getWriters() {
        return writers;
    }

    public void setWriters(List<NameBasics> writers) {
        this.writers = writers;
    }
}
