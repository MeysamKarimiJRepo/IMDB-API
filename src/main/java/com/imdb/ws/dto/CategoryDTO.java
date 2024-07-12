package com.imdb.ws.dto;

import java.util.List;

public class CategoryDTO {

    private Long id;
    private List<TitlePrincipalDTO> principals;
    private String name;

    public CategoryDTO() {}

    public CategoryDTO(Long id, List<TitlePrincipalDTO> principals, String name) {
        this.id = id;
        this.principals = principals;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TitlePrincipalDTO> getPrincipals() {
        return principals;
    }

    public void setPrincipals(List<TitlePrincipalDTO> principals) {
        this.principals = principals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
