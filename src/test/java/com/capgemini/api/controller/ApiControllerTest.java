package com.capgemini.api.controller;

import com.capgemini.api.dto.RecipeDto;
import com.capgemini.api.dto.RecipeIngredientDto;
import com.capgemini.utill.RecipeServiceConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Test
    public void testAddRecipeSuccess() throws URISyntaxException
    {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:"+randomServerPort+"/recipes";
        URI uri = new URI(baseUrl);
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setRecipeName("Test1");
        recipeDto.setCuisine("ABC");
        recipeDto.setCookingInstructions("do this and do that");
        recipeDto.setDishType(RecipeServiceConstants.FoodDishType.VEGETARIN);
        ArrayList<RecipeIngredientDto> recipeIngredientDtos = new ArrayList<>();
        recipeIngredientDtos.add(new RecipeIngredientDto("Milk",8.0));
        recipeDto.setIngredients(recipeIngredientDtos);
        HttpEntity<RecipeDto> request = new HttpEntity<>(recipeDto);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        Assertions.assertEquals(201, result.getStatusCodeValue());
    }

}
