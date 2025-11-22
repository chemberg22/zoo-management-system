package com.zoo.zoo_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CareTypeRequest(
        @NotBlank @Size(max = 64) String name,
        @Size(max = 256) String description,
        @Size(max = 32) String frequency
) { }