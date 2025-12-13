package com.grandchefsupreme.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_list", schema = "public")
@Getter
@Setter
@ToString(exclude = {"client", "items"})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne @JoinColumn(name = "id_user", nullable = false)
    private Client client;

    @Column(name = "creation_date", nullable = false)
    @CreationTimestamp
    private Timestamp creationDate;

    @Column (name = "status",  nullable= false)
    private Boolean status;

    @OneToMany(mappedBy = "shoppingList", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingListIngredient> items = new ArrayList<>();
}

