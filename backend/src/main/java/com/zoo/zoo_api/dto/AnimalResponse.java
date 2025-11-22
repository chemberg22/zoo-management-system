package com.zoo.zoo_api.dto;

import java.time.LocalDate;

public record AnimalResponse(
        Integer id,
        String name,
        String description,
        LocalDate birthDate,
        LocalDate registrationDate,

        SpeciesResponse species,
        HabitatResponse habitat,
        CountryResponse country
) { }