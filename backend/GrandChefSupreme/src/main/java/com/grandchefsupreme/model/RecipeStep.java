package com.grandchefsupreme.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recipe_step")
@Getter
@Setter
@ToString(exclude = "recipe")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RecipeStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_recipe", nullable = false)
    private Recipe recipe;

    @Column(name = "step_number", nullable = false)
    private Integer stepNumber;

    @Column(name="instruction", nullable = false)
    private String instruction;
}
