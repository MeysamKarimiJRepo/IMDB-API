package com.imdb.ws.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "name_basics")
public class NameBasics {

    @Id
    @Column(name = "nconst")
    private String nconst;

    private String primaryName;
    private Integer birthYear;
    private Integer deathYear;

    @ElementCollection
    private List<String> primaryProfession;

    @ElementCollection
    private List<String> knownForTitles;

    @OneToMany(mappedBy = "nameBasics")
    private List<TitlePrincipals> principals;

    @ManyToMany(mappedBy = "directors")
    private List<TitleCrew> directedTitles;

    @ManyToMany(mappedBy = "writers")
    private List<TitleCrew> writtenTitles;

    public String getNconst() {
        return nconst;
    }

    public void setNconst(String nconst) {
        this.nconst = nconst;
    }

    public String getPrimaryName() {
        return primaryName;
    }

    public void setPrimaryName(String primaryName) {
        this.primaryName = primaryName;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public List<String> getPrimaryProfession() {
        return primaryProfession;
    }

    public void setPrimaryProfession(List<String> primaryProfession) {
        this.primaryProfession = primaryProfession;
    }

    public List<String> getKnownForTitles() {
        return knownForTitles;
    }

    public void setKnownForTitles(List<String> knownForTitles) {
        this.knownForTitles = knownForTitles;
    }

    public List<TitlePrincipals> getPrincipals() {
        return principals;
    }

    public void setPrincipals(List<TitlePrincipals> principals) {
        this.principals = principals;
    }

    public List<TitleCrew> getDirectedTitles() {
        return directedTitles;
    }

    public void setDirectedTitles(List<TitleCrew> directedTitles) {
        this.directedTitles = directedTitles;
    }

    public List<TitleCrew> getWrittenTitles() {
        return writtenTitles;
    }

    public void setWrittenTitles(List<TitleCrew> writtenTitles) {
        this.writtenTitles = writtenTitles;
    }
}
