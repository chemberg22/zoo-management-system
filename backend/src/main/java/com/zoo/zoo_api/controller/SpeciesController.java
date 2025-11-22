package com.zoo.zoo_api.controller;

import com.zoo.zoo_api.model.Species;
import com.zoo.zoo_api.service.SpeciesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/species")
public class SpeciesController {

    private final SpeciesService service;

    public SpeciesController(SpeciesService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Species>> findAllSpecies() {
        List<Species> species = service.findAll();
        return ResponseEntity.ok(species);
    }
}
