package com.zoo.zoo_api.dto;

public record CareTypeResponse(
        Integer id,
        String name,
        String description,
        String frequency
) { }