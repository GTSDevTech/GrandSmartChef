package com.grandchefsupreme.model;


import com.grandchefsupreme.model.Enums.Unit;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "recipe_ingredient")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"recipe", "ingredient"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_recipe")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "id_ingredient")
    private Ingredient ingredient;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "unit", nullable=false)
    @Enumerated(EnumType.STRING)
    private Unit unit;
}