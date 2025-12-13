package com.grandchefsupreme.repository;

import com.grandchefsupreme.model.FavoriteCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteCollectionRepository extends JpaRepository<FavoriteCollection, Long> {

    List<FavoriteCollection> findByClientId(Long client_id);


}
