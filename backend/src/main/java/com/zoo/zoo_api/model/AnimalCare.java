package com.zoo.zoo_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "AnimalCare")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AnimalCare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Animal is required!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AnimalId", nullable = false)
    private Animal animal;

    @NotNull(message = "Care type is required!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CareTypeId", nullable = false)
    private CareType careType;

    @NotNull(message = "Realization date is required!")
    @Column(nullable = false)
    private LocalDate realizationDate;

    @Size(max = 1024)
    private String observations;
}