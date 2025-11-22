package com.zoo.zoo_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "animals")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Animal {

    // ID generated sequentially
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Required name, max 64
    @NotBlank(message = "Animal name is required!")
    @Size(max = 64)
    @Column(nullable = false)
    private String name;

    // Not required description, max 512
    @Size(max = 512)
    private String description;

    // Birthdate not required
    private LocalDate birthDate;

    // Required species, pre setted in database
    @NotNull(message = "Species is required!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SpeciesId", nullable = false)
    private Species species;

    // Required habitat, pre setted in database
    @NotNull(message = "Habitat is required!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HabitatId", nullable = false)
    private Habitat habitat;

    // Birthplace (country), pre setted in database
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BirthPlaceId", nullable = true)
    private Country birthPlace;

    // Required and immutable registration date
    @Column(nullable = false, updatable = false)
    private LocalDate registrationDate = LocalDate.now();

    // Ensure registration date case null
    @PrePersist
    private void garanteeRegistrationDate() {
        if (this.registrationDate == null) {
            this.registrationDate = LocalDate.now();
        }
    }
}