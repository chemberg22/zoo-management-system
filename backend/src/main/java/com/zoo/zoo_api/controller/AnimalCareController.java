package com.zoo.zoo_api.controller;

import com.zoo.zoo_api.dto.*;
import com.zoo.zoo_api.service.AnimalCareService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animal-cares")
public class AnimalCareController {

    private final AnimalCareService service;

    public AnimalCareController(AnimalCareService service) {
        this.service = service;
    }

    @GetMapping
    public List<AnimalCareResponse> findAll(@RequestParam(required = false) Integer animalId) {
        return service.findAll(animalId);
    }

    @GetMapping("/animal/{animalId}")
    public List<AnimalCareResponse> findByAnimalId(@PathVariable Integer animalId) {
        return service.findByAnimalId(animalId);
    }

    @GetMapping("/{id}")
    public AnimalCareResponse findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalCareResponse create(@Valid @RequestBody AnimalCareRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public AnimalCareResponse update(@PathVariable Integer id, @Valid @RequestBody AnimalCareRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}