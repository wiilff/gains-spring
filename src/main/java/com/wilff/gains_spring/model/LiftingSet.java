package com.wilff.gains_spring.model;

import java.time.Instant;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
    @Column(updatable = false)
    private Instant loggedAt;

    @ManyToOne
    @JoinColumn(name = "workout_exercise_id", nullable = false)
    @JsonBackReference
    private WorkoutExercise workoutExercise;
} 
    
