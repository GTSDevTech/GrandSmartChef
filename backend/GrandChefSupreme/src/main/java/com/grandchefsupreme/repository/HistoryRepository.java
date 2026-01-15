package com.grandchefsupreme.repository;

import com.grandchefsupreme.model.History;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query(value = """
    select DISTINCT h.*
    from history h
    left join public.client c on h.id_user = c.id
    left join public.recipe r on h.id_recipe = r.id
     WHERE c.id = :clientId
          AND h.date BETWEEN :startDate AND :endDate
        order by h.date DESC
    """, nativeQuery = true)
    List<History> findAllFromLast7Days(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("clientId") Long clientId
    );

    Long countByClient_Id(@Valid Long clientId);
}
