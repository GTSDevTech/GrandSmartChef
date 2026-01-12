package com.grandchefsupreme.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "recipe_rating",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"id_recipe", "id_user"}
        )
)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recipe", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private Client client;

    @Column(name = "stars", nullable = false)
    private Integer rating;

    @Column(name = "rating_date")
    private LocalDateTime ratingDate;

    @Column(name = "review", length = 400)
    private String review;
}
