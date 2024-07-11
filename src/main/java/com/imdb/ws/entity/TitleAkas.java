package com.imdb.ws.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * reviewed
 */
@Entity
@Table(name = "title_akas")
public class TitleAkas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for the entity

    @Column(name = "ordering")
    private int ordering;

    @Column(name = "title", length = 1024)
    private String title;

    @Column(name = "region")
    private String region;

    @Column(name = "language")
    private String language;

    @ElementCollection
    @Column(name = "types")
    private List<String> types;

    @ElementCollection
    @Column(name = "attributes")
    private List<String> attributes;

    @Column(name = "is_original_title")
    private boolean isOriginalTitle;

    @ManyToOne
    @JoinColumn(name = "title_id", nullable = false)
    private TitleBasic titleBasic;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public boolean isOriginalTitle() {
        return isOriginalTitle;
    }

    public void setOriginalTitle(boolean originalTitle) {
        isOriginalTitle = originalTitle;
    }

    public TitleBasic getTitleBasics() {
        return titleBasic;
    }

    public void setTitleBasics(TitleBasic titleBasic) {
        this.titleBasic = titleBasic;
    }
}