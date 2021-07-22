package com.capgemini.api.dto;

import com.capgemini.utill.RecipeServiceConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class RecipeDto {

    private long id;
    @NotBlank(message = "Name is mandatory")
    private String recipeName;
    @JsonManagedReference
    private List<RecipeIngredientDto> ingredients;
    @NotBlank(message = "cooking instructions is mandatory")
    private String cookingInstructions;
    private String cuisine;
    @NotBlank(message = "suitable for how many # of person is mandatory")
    private String suitableFor; // suitable For # of person
    private RecipeServiceConstants.FoodDishType dishType;
    @JsonFormat(pattern = "dd‐MM‐yyyy HH:mm", timezone = "Europe/Amsterdam")
    private LocalDateTime createdDate;

    @JsonProperty
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<RecipeIngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredientDto> ingredients) {
        this.ingredients = ingredients;
    }

    public String getCookingInstructions() {
        return cookingInstructions;
    }

    public void setCookingInstructions(String cookingInstructions) {
        this.cookingInstructions = cookingInstructions;
    }

    public String getSuitableFor() {
        return suitableFor;
    }

    public void setSuitableFor(String suitableFor) {
        this.suitableFor = suitableFor;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public RecipeServiceConstants.FoodDishType getDishType() {
        return dishType;
    }

    public void setDishType(RecipeServiceConstants.FoodDishType dishType) {
        this.dishType = dishType;
    }
}
