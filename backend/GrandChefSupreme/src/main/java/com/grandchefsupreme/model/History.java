package com.grandchefsupreme.model;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "history")
@Getter
@Setter
@ToString (exclude = {"recipe", "client"})
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_recipe", nullable = false)
    private Recipe recipe;

    @Column(name = "date", nullable = false)
    private LocalDate date;


}
