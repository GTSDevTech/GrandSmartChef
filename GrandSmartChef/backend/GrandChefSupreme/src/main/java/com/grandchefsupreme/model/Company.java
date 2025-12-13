package com.grandchefsupreme.model;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table (name = "company", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer idCompany;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "tax_id", nullable = false, length = 10, unique = true)
    private String cif;

    @Column(name = "address", length = 20)
    private String address;

    @Column(name = "postal_code", nullable = false, unique = true, length = 100)
    private String postalCode;

    @Column(name = "google_maps_url", nullable = false, length = 10)
    private String googleMapsUrl;
}
