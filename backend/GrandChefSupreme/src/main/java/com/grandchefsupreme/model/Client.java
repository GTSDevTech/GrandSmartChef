package com.grandchefsupreme.model;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "client", schema = "public")
@DiscriminatorValue("1")
@PrimaryKeyJoinColumn(name = "id")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"favoriteCollections", "shoppingLists", "ratings", "histories"})
@EqualsAndHashCode(callSuper = false)
public class Client extends User {

    @Column(name = "full_name", length = 150)
    private String fullName;

    @Column(name = "birth_date")
    private LocalDate birthdate;

    @Column(name = "country", length = 50)
    private String country;

    @Column (name = "photo_url", length = 500)
    private String photoProfile;


    @OneToMany (mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteCollection> favoriteCollections = new ArrayList<>();

    @OneToMany (mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingList> shoppingLists = new ArrayList<>();

    @OneToMany (mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeRating> ratings = new ArrayList<>();

    @OneToMany (mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<History> histories = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "client_tag",
        joinColumns = @JoinColumn(name = "id_client"),
        inverseJoinColumns = @JoinColumn(name = "id_tag"))
    private Set<Tag> preferences;

    @Override
    public String getRoleName() {
        return "ROLE_CLIENT";
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

}

