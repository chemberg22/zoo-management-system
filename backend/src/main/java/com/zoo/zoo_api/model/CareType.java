package com.zoo.zoo_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "CareType")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CareType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Care type name is required!")
    @Size(max = 64)
    @Column(nullable = false)
    private String name;

    @Size(max = 256)
    private String description;

    @Size(max = 32)
    private String frequency;
}