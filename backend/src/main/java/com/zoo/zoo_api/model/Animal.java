package com.zoo.zoo_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "Animal")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Animal name is required!")
    @Size(max = 64)
    @Column(nullable = false)
    private String name;

    @Size(max = 512)
    private String description;

    private LocalDate birthDate;

    @NotNull(message = "Species is required!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SpeciesId", nullable = false)
    private Species species;

    @NotNull(message = "Habitat is required!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HabitatId", nullable = false)
    private Habitat habitat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BirthPlaceId", nullable = true)
    private Country birthPlace;

    @Column(nullable = false, updatable = false)
    private LocalDate registrationDate = LocalDate.now();

    @PrePersist
    private void garantirRegistrationDate() {
        if (this.registrationDate == null) {
            this.registrationDate = LocalDate.now();
        }
    }
}