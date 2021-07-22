package com.capgemini.api.controller;

import com.capgemini.api.dto.RecipeDto;
import com.capgemini.exception.NotImplementedException;
import com.capgemini.exception.RecipeNotFoundException;
import com.capgemini.persistence.model.Recipe;
import com.capgemini.service.RecipeService;
import org.apache.commons.lang3.Validate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.capgemini.utill.RecipeServiceConstants.ARG_NULL_ERROR_MESSAGE;

@RestController
@RequestMapping(path = {"/api/*"}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
public class ApiController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final RecipeService recipeService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ApiController(RecipeService recipeService) {
        Validate.notNull(recipeService, ARG_NULL_ERROR_MESSAGE, "recipeService");
        this.recipeService = recipeService;
    }

    @GetMapping(path = {"/recipes"})
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        logger.info("Get recipes was called");
        List<Recipe> recipes = recipeService.getAllRecipes();
        return new ResponseEntity<>(recipes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/recipes/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Recipe recipe = recipeService.getRecipe(id).orElseThrow(() -> new ResourceNotFoundException("Recipe Not found with id = " + id));
        RecipeDto recipeDto = modelMapper.map(recipe, RecipeDto.class);
        return new ResponseEntity<>(recipeDto, HttpStatus.OK);
    }

    @PutMapping(value = "/recipes/{id}")
    public ResponseEntity<HttpStatus> updateRecipe(@PathVariable("id") Long id, @RequestBody RecipeDto recipeDto) {
        recipeService.getRecipe(id).orElseThrow(() -> new ResourceNotFoundException("Recipe Not found for update with id = " + id));
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
        recipeService.updateRecipe(recipe);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable("id") long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/recipes")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public RecipeDto createRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        Recipe recipe = convertToEntity(recipeDto);
        Recipe recipeCreated = recipeService.createRecipe(recipe);
        return convertToDto(recipeCreated);
    }

    private Recipe convertToEntity(RecipeDto recipeDto) {
        return modelMapper.map(recipeDto, Recipe.class);
    }

    private RecipeDto convertToDto(Recipe recipe) {
        RecipeDto recipeDto = modelMapper.map(recipe, RecipeDto.class);
        recipeDto.setCreatedDate(recipe.getCreatedDate());
        return recipeDto;
    }
}
