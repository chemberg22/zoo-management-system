package com.zoo.zoo_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "countries")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Country {

    // ID generated sequentially
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Country name required, max 64
    @NotBlank(message = "Country name is required!")
    @Size(max = 64)
    @Column(nullable = false, unique = true)
    private String name;
}