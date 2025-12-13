package com.grandchefsupreme.model;

import com.grandchefsupreme.model.Enums.Unit;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredient", schema = "public")
@Getter
@Setter
@ToString(exclude = {"recipeIngredients", "ingredientCategory"})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 150)
    private String name;

    @Column(name = "calories", length = 500)
    private BigDecimal calories;

    @Column(name = "proteins", length = 300)
    private BigDecimal proteins;

    @Column(name = "carbs", length = 300)
    private BigDecimal carbs;

    @Column(name = "fats", length = 300)
    private BigDecimal fats;

    @Column (name = "unit")
    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Column (name= "photo_url")
    private String photoUrl;

    @Column (name = "is_active", nullable = false)
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn (name = "id_ingredient_category", nullable = false)
    private IngredientCategory ingredientCategory;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();



}
