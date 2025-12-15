package com.grandchefsupreme.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "favorite_collection", schema = "public")
@Getter @Setter
@ToString (exclude = {"client", "recipes"})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FavoriteCollection {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column (name = "title", nullable = false, length = 100)
    private String title;

    @Column (name = "color", length = 9)
    private String color;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column (name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn (name = "id_user", nullable = false)
    private Client client;

    @ManyToMany
    @JoinTable(
            name = "favorite_collection_recipe",
            joinColumns = @JoinColumn(name = "id_collection"),
            inverseJoinColumns = @JoinColumn(name = "id_recipe")
    )
    private List<Recipe> recipes = new ArrayList<>();





}
