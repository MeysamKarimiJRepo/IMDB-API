package com.imdb.ws.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "title_principal",uniqueConstraints = { @UniqueConstraint(columnNames = { "tconst", "ordering" }) })
public class TitlePrincipal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ordering")
    private int ordering;

    @ManyToOne
    @JoinColumn(name = "tconst", nullable = false)
    private TitleBasic titleBasic;

    @ManyToOne
    @JoinColumn(name = "nconst", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    private String job;
    @Column(length = 2000)
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

    public TitleBasic getTitleBasics() {
        return titleBasic;
    }

    public void setTitleBasics(TitleBasic titleBasic) {
        this.titleBasic = titleBasic;
    }

    public Person getNameBasics() {
        return person;
    }

    public void setNameBasics(Person person) {
        this.person = person;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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