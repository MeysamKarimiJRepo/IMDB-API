package com.imdb.ws.dto;

public class TitlePrincipalDTO {

    private Long id;
    private int ordering;
    private TitleBasicDTO titleBasic;
    private PersonDTO person;
    private CategoryDTO category;
    private String job;
    private String characters;

    // Constructors, getters, and setters

    public TitlePrincipalDTO() {}

    public TitlePrincipalDTO(Long id, int ordering, TitleBasicDTO titleBasic, PersonDTO person, CategoryDTO category, String job, String characters) {
        this.id = id;
        this.ordering = ordering;
        this.titleBasic = titleBasic;
        this.person = person;
        this.category = category;
        this.job = job;
        this.characters = characters;
    }

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

    public TitleBasicDTO getTitleBasic() {
        return titleBasic;
    }

    public void setTitleBasic(TitleBasicDTO titleBasic) {
        this.titleBasic = titleBasic;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
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
