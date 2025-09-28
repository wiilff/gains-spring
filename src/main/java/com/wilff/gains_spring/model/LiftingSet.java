package com.wilff.gains_spring.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LiftingSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double reps;
    private double weight;
    private int setOrder;
    private LocalDateTime loggedAt;

    @ManyToOne
    @JoinColumn(name = "workout_exercise_id", nullable = false)
    @JsonBackReference
    private WorkoutExercise workoutExercise;
} 
    
