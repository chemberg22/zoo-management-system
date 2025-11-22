package com.zoo.zoo_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "Habitat")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Habitat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Habitat name is required!")
    @Size(max = 64)
    @Column(nullable = false)
    private String name;

    @Size(max = 512)
    private String description;
}