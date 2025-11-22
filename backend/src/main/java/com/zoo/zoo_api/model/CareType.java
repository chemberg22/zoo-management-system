package com.zoo.zoo_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "careTypes")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CareType {

    // ID generated sequentially
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Care type name required, max 64
    @NotBlank(message = "Care type name is required!")
    @Size(max = 64)
    @Column(nullable = false)
    private String name;

    // Description not required, max 256
    @Size(max = 256)
    private String description;

    // Frequency not required, max 32
    @Size(max = 32)
    private String frequency;
}