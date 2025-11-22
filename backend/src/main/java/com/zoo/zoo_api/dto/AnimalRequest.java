package com.zoo.zoo_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record AnimalRequest(
        @NotBlank @Size(max = 64) String name,
        @Size(max = 512) String description,
        LocalDate birthDate,

        @NotNull Integer speciesId,
        @NotNull Integer habitatId,
        Integer birthPlaceId
) { }