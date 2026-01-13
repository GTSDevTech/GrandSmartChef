package com.grandchefsupreme;
import com.grandchefsupreme.model.Enums.Difficulty;
import com.grandchefsupreme.model.Recipe;
import com.grandchefsupreme.repository.RecipeRepository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class RecipeServiceTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeAll
    void setUp() {
        Recipe recipe1 = new Recipe();
        recipe1.setName("Spaghetti Carbonara");
        recipe1.setDifficulty(Difficulty.MEDIA);
        recipe1.setServings(2);
        recipe1.setPrepTime(25.0);
        recipe1.setDescription("Classic Italian pasta");
        recipe1.setImageUrl("carbonara.jpg");
        recipe1.setIsActive(true);

        Recipe recipe2 = new Recipe();
        recipe2.setName("Grilled Chicken");
        recipe2.setDifficulty(Difficulty.FACIL);
        recipe2.setServings(4);
        recipe2.setPrepTime(40.0);
        recipe2.setDescription("Healthy grilled chicken");
        recipe2.setImageUrl("chicken.jpg");
        recipe2.setIsActive(true);

        Recipe recipe3 = new Recipe();
        recipe3.setName("Chocolate Soufflé");
        recipe3.setDifficulty(Difficulty.DIFICIL);
        recipe3.setServings(1);
        recipe3.setPrepTime(60.0);
        recipe3.setDescription("French dessert");
        recipe3.setImageUrl("souffle.jpg");
        recipe3.setIsActive(false);

        recipeRepository.saveAll(List.of(recipe1, recipe2, recipe3));
    }

    @Test
    void testRecipeCount() {
        long count = recipeRepository.count();
        assertEquals(3, count);

    }

    @Test
    void testExistRecipe(){

        assertNotNull(recipeRepository.findById(1L));
    }
}