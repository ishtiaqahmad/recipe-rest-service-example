package com.capgemini.service;

import com.capgemini.persistence.model.Recipe;
import com.capgemini.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        logger.info("Getting All recipes");
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipe(Long id) {
        logger.info("Getting Recipe by ID {}", id);
        return recipeRepository.findById(id);
    }

    public Recipe createRecipe(Recipe recipe) {
        logger.info("Saving Recipe");
        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(Recipe recipe) {
        logger.info("Updated Recipe");
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id)  {
        logger.info("Deleting Recipe by ID {}", id);
        recipeRepository.deleteById(id);
    }

}
