package com.wilff.gains_spring.repository;

import com.wilff.gains_spring.model.Split;
import com.wilff.gains_spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SplitRepository extends JpaRepository<Split, Integer> {
    @Query("SELECT s FROM Split s " +
            "LEFT JOIN FETCH s.trainingDays td " +
            "LEFT JOIN FETCH td.exercises e " +
            "WHERE s.user.id = :userId")
    List<Split> findAllByUserId(int userId);
}
