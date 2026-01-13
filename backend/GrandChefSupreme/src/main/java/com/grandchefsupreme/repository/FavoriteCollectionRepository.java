package com.grandchefsupreme.repository;

import com.grandchefsupreme.model.FavoriteCollection;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteCollectionRepository extends JpaRepository<FavoriteCollection, Long> {

    List<FavoriteCollection> findByClientId(Long client_id);


    Long countFavoriteCollectionByClient_Id(Long clientId);


    @Query(value = "SELECT COUNT(fcr.id_recipe) FROM favorite_collection fc " +
            "JOIN favorite_collection_recipe fcr ON fc.id = fcr.id_collection " +
            "WHERE fc.id_user = :clientId", nativeQuery = true)
    Long countFavoriteRecipesByClientId(@Valid Long clientId);
}
