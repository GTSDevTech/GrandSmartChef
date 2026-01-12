package com.grandchefsupreme.mapper;

import com.grandchefsupreme.dto.RecipeCardDTO;
import com.grandchefsupreme.model.Recipe;
import com.grandchefsupreme.model.RecipeRating;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeCardMapper {

    @Mapping(source = "recipe", target = "averageRating", qualifiedByName = "average_rating_calc")
    RecipeCardDTO toDTO(Recipe recipe);

    @Mapping(target = "imageUrl", ignore = true)
    Recipe toEntity(RecipeCardDTO recipeCardDTO);

    @Mapping(source = "recipe", target = "averageRating", qualifiedByName = "average_rating_calc")
    List<RecipeCardDTO> toDTO(List<Recipe> recipes);

    @Mapping(target = "imageUrl", ignore = true)
    List<Recipe> toEntity(List<RecipeCardDTO> recipeCardDTOList);


    @Named("average_rating_calc")
    default Double calculateAvgRating(Recipe recipe) {
        if (recipe.getRatings() == null || recipe.getRatings().isEmpty()) {
            return 0.0;
        }
        return recipe.getRatings()
                .stream()
                .mapToDouble(RecipeRating::getRating)
                .average()
                .orElse(0.0);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRecipeFromDto(RecipeCardDTO dto, @MappingTarget Recipe entity);


}
