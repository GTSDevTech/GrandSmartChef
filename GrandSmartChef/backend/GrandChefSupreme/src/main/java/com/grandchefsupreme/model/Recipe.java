package com.grandchefsupreme.model;

import com.grandchefsupreme.model.Enums.Difficulty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table (name = "recipe", schema = "public")
@Getter @Setter
@ToString(exclude = {"ratings", "histories", "recipeIngredients", "shoppingListIngredients", "tags"})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "difficulty")
    @Enumerated(EnumType.ORDINAL)
    private Difficulty difficulty;

    @Column(name = "servings", length = 2)
    private Integer servings;

    @Column(name = "prep_time", length = 20)
    private Double prepTime;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "photo_url", length = 500)
    private String imageUrl;

    @Column (name = "creation_date")
    @CreationTimestamp
    private Timestamp creationDate;

    @Column (name = "updated_at")
    @UpdateTimestamp
    private Timestamp updateDate;

    @Column (name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingListIngredient> shoppingListIngredients = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "recipe_tag",
        joinColumns = @JoinColumn(name = "id_recipe"),
        inverseJoinColumns = @JoinColumn(name = "id_tag")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany (mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<History> histories = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeRating> ratings = new HashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("stepNumber ASC")
    private List<RecipeStep> recipeSteps = new ArrayList<>();


}
