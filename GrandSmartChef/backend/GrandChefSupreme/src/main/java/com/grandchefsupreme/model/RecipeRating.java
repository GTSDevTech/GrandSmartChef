package com.grandchefsupreme.model;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "recipe_rating")
@Getter
@Setter
@ToString(exclude = {"recipe", "client"})
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RecipeRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_recipe", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private Client client;

    @Column(name = "stars", nullable = false)
    private Integer rating;

    @Column(name = "rating_date")
    private Timestamp ratingDate;

    @Column(name = "review", length = 400)
    private String review;
}
