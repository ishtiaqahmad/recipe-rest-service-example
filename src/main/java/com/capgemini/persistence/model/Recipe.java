package com.capgemini.persistence.model;

import com.capgemini.utill.RecipeServiceConstants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String recipeName;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RecipeIngredient> ingredients = new ArrayList<>();
    @Column(length=10485760)
    private String cookingInstructions;
    private String cuisine;
    private String suitableFor; // suitable For # of person
    private RecipeServiceConstants.FoodDishType dishType;

    @Column(name="created_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdDate;

    public Recipe() {
        // default
        this.createdDate =  LocalDateTime.now();
        this.dishType = RecipeServiceConstants.FoodDishType.NON_VEGETARIN;
    }

    public Recipe(String recipeName, List<RecipeIngredient> ingredients) {
        this();
        this.recipeName = recipeName;
        this.ingredients = ingredients;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }


    public String getRecipeName() {
        return recipeName;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
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

    public RecipeServiceConstants.FoodDishType getDishType() {
        return dishType;
    }

    public void setDishType(RecipeServiceConstants.FoodDishType dishType) {
        this.dishType = dishType;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
