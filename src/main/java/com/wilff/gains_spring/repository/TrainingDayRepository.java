package com.wilff.gains_spring.repository;

import com.wilff.gains_spring.model.TrainingDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingDayRepository extends JpaRepository<TrainingDay, Integer> {
}
