package com.grandchefsupreme.model;


import com.grandchefsupreme.model.Enums.Tags;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "tag")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column (name = "name", nullable = false, unique = true, length = 100)
    private Tags name;

}
