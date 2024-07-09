package com.imdb.ws.entity;

import jakarta.persistence.*;

/**
 * reviewed
 */
@Entity
@Table(name = "title_principals")
public class TitlePrincipals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for the entity

    @Column(name = "ordering")
    private int ordering;

    @ManyToOne
    @JoinColumn(name = "tconst", nullable = false)
    private TitleBasics titleBasics;

    @ManyToOne
    @JoinColumn(name = "nconst", nullable = false)
    private NameBasics nameBasics;

    private String category;
    private String job;
    private String characters;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public TitleBasics getTitleBasics() {
        return titleBasics;
    }

    public void setTitleBasics(TitleBasics titleBasics) {
        this.titleBasics = titleBasics;
    }

    public NameBasics getNameBasics() {
        return nameBasics;
    }

    public void setNameBasics(NameBasics nameBasics) {
        this.nameBasics = nameBasics;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }
}