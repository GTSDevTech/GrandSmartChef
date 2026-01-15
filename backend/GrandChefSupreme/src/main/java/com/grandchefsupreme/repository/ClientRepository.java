package com.grandchefsupreme.repository;

import com.grandchefsupreme.dto.TopClientDTO;
import com.grandchefsupreme.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {


    Boolean existsByEmail(String email);


    @Query(value = """
    SELECT DISTINCT c.id, u.username, u.email
    FROM public."user" u
    JOIN public.client c
        ON u.id = c.id
    JOIN public.favorite_collection fc
        ON c.id = fc.id_user
    JOIN favorite_collection_recipe fcr
        ON fc.id = fcr.id_collection
    WHERE fcr.id_recipe = (
        SELECT id_recipe
        FROM favorite_collection_recipe
        GROUP BY id_recipe
        ORDER BY COUNT(*) DESC
    	LIMIT 1)
    """, nativeQuery = true)
    List<TopClientDTO> findClientByTopRecipes();

}
