package com.capgemini.api.dto;

import com.capgemini.persistence.model.Recipe;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RecipeIngredientDto {
    private long id;
    @NotNull(message = "ingredient name can not be null!")
    private String name;
    @NotNull(message = "quantity can not be null!")
    private Double quantity; // in grams

    public RecipeIngredientDto(String name, Double quantity) {
        this.name = name;
        this.quantity = quantity;
    }
    public RecipeIngredientDto(long id, String name, Double quantity) {
        this(name,quantity);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
