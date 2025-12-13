package com.grandchefsupreme.repository;

import com.grandchefsupreme.dto.ClientDTO;
import com.grandchefsupreme.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findTopByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByUsernameAndPassword(String username, String password);



}
