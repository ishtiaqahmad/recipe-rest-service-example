package com.capgemini;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.web.client.RestTemplateBuilder;

public class RecipeServiceApplicationTest extends AbstractTest {


    private static Class<RecipeServiceApplication> classUnderTest;
    @Mock
    private RestTemplateBuilder builder;

    @InjectMocks
    private RecipeServiceApplication appUnderTest;

    @BeforeAll
    public static void init() {
        classUnderTest = RecipeServiceApplication.class;
    }

    @Test
    public void hasMainMethod() throws NoSuchMethodException, SecurityException {
        Assertions.assertNotNull(classUnderTest.getDeclaredMethod("main", String[].class));
    }
}
