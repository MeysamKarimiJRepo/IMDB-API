package com.imdb.ws.dto;

import java.util.List;

public class PersonDTO {

    private String nconst;
    private String primaryName;
    private Integer birthYear;
    private Integer deathYear;
    private List<String> primaryProfession;
    private List<String> knownForTitles;
    private List<TitlePrincipalDTO> principals;

    // Constructors, getters, and setters

    public PersonDTO() {}

    public PersonDTO(String nconst, String primaryName, Integer birthYear, Integer deathYear, List<String> primaryProfession, List<String> knownForTitles, List<TitlePrincipalDTO> principals) {
        this.nconst = nconst;
        this.primaryName = primaryName;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.primaryProfession = primaryProfession;
        this.knownForTitles = knownForTitles;
        this.principals = principals;
    }

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

    public List<TitlePrincipalDTO> getPrincipals() {
        return principals;
    }

    public void setPrincipals(List<TitlePrincipalDTO> principals) {
        this.principals = principals;
    }
}
