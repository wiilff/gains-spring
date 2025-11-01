package com.wilff.gains_spring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name; // e.g., "Day 1 - Push"

    @ManyToOne
    @JoinColumn(name = "split_id", nullable = false)
    private Split split;

    @ManyToMany
    @JoinTable(
            name = "training_day_exercise",
            joinColumns = @JoinColumn(name = "training_day_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    private Set<Exercise> exercises = new HashSet<>();
}
