package com.grandchefsupreme.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredient_category", schema = "public")
@Getter
@Setter
@ToString(exclude = {"ingredients"})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class IngredientCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 150)
    private String name;

    @Column (name = "description", length = 500)
    private String description;

    @Column (name="photo_url", nullable = false)
    private String photoUrl;

    @OneToMany (mappedBy = "ingredientCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients = new ArrayList<>();

}
