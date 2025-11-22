package com.zoo.zoo_api.dto;

import java.time.LocalDate;

public record AnimalCareResponse(
        Integer id,
        LocalDate realizationDate,
        String observations,
        AnimalSimpleResponse animal,
        CareTypeResponse careType
) { }