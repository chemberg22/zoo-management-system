package com.zoo.zoo_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record AnimalCareRequest(
        @NotNull Integer animalId,
        @NotNull Integer careTypeId,
        LocalDate realizationDate,
        @Size(max = 1024) String observations
) { }