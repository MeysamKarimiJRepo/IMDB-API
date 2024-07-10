package com.imdb.ws.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for the entity

    @OneToMany(mappedBy = "category")
    private List<TitlePrincipals> principals;

    @Column(name = "name", unique = true)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TitlePrincipals> getPrincipals() {
        return principals;
    }

    public void setPrincipals(List<TitlePrincipals> principals) {
        this.principals = principals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
