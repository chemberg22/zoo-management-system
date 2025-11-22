package com.zoo.zoo_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "habitats")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Habitat {

    // ID generated sequentially
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Habitat name required, max 64
    @NotBlank(message = "Habitat name is required!")
    @Size(max = 64)
    @Column(nullable = false)
    private String name;

    // Description not required, max 512
    @Size(max = 512)
    private String description;
}