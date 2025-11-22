package com.zoo.zoo_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "species")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Species {

    // ID generated sequentially
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Species name required, max 128
    @NotBlank(message = "Species name is required!")
    @Size(max = 128)
    @Column(nullable = false, unique = true)
    private String name;
}