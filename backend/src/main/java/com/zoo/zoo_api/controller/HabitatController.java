package com.zoo.zoo_api.controller;

import com.zoo.zoo_api.model.Habitat;
import com.zoo.zoo_api.service.HabitatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/habitats")
public class HabitatController {

    private final HabitatService service;

    public HabitatController(HabitatService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Habitat>> findAllHabitats() {
        List<Habitat> habitat = service.findAll();
        return ResponseEntity.ok(habitat);
    }
}
