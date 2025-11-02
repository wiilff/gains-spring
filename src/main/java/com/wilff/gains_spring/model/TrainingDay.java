package com.wilff.gains_spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrainingDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "split_id", nullable = false)
    private Split split;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "training_day_exercise",
            joinColumns = @JoinColumn(name = "training_day_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    private Set<Exercise> exercises = new HashSet<>();
}
