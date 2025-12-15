package com.grandchefsupreme.repository;

import com.grandchefsupreme.model.ShoppingList;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {


    Optional<ShoppingList> findByClientIdAndStatus(Long userId, Boolean status);


    @Query("SELECT sl FROM ShoppingList sl LEFT JOIN FETCH sl.items WHERE sl.id = :id")
    Optional<ShoppingList> findByIdWithItems(@Param("id") Long id);


    List<ShoppingList> findAllByClientId(Long userId);

     @Modifying
     @Query("""
        DELETE FROM ShoppingList sl
        WHERE sl.client.id = :userId
          AND NOT EXISTS (
            SELECT 1 FROM ShoppingListIngredient sli
            WHERE sli.shoppingList.id = sl.id
          )
        """)
    void deleteAllEmptyListsByUserId(@Param("userId") Long userId);

}
