package com.grandchefsupreme.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "demo", schema = "public")
@DiscriminatorValue("2")
@PrimaryKeyJoinColumn(name = "id")
@Getter @Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class Demo extends User{


    @Override
    public String getRoleName() {
        return "ROLE_DEMO";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
