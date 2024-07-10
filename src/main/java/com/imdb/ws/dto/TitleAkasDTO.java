package com.imdb.ws.dto;

import java.util.List;

public class TitleAkasDTO {

    private Long id;
    private int ordering;
    private String title;
    private String region;
    private String language;
    private List<String> types;
    private List<String> attributes;
    private boolean originalTitle;
    private String titleBasicTconst;

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
        return originalTitle;
    }

    public void setOriginalTitle(boolean originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTitleBasicTconst() {
        return titleBasicTconst;
    }

    public void setTitleBasicTconst(String titleBasicTconst) {
        this.titleBasicTconst = titleBasicTconst;
    }
}
