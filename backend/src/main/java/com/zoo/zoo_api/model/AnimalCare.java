package com.zoo.zoo_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "animalCares")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AnimalCare {

    // ID generated sequentially
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Animal ID required
    @NotNull(message = "Animal is required!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AnimalId", nullable = false)
    private Animal animal;

    // Care type ID required
    @NotNull(message = "Care type is required!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CareTypeId", nullable = false)
    private CareType careType;

    // Realization date required
    @NotNull(message = "Realization date is required!")
    @Column(nullable = false)
    private LocalDate realizationDate;

    // Observations not required, max 1024
    @Size(max = 1024)
    private String observations;
}