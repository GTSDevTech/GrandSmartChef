package com.grandchefsupreme.model;


import com.grandchefsupreme.model.Enums.Unit;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "shopping_list_ingredient", schema = "public")
@Getter
@Setter
@ToString(exclude = {"shoppingList", "recipe", "ingredient"})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ShoppingListIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_shopping_list", nullable = false)
    @EqualsAndHashCode.Include
    private ShoppingList shoppingList;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recipe", nullable = false)
    @EqualsAndHashCode.Include
    private Recipe recipe;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ingredient", nullable = false)
    @EqualsAndHashCode.Include
    private Ingredient ingredient;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Column (name = "unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Column (name = "bought")
    private Boolean bought;


}
