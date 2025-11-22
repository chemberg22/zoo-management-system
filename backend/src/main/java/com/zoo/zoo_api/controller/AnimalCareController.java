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

    // Link to animal care service
    private final AnimalCareService service;

    // Immutable
    public AnimalCareController(AnimalCareService service) {
        this.service = service;
    }

    // GET method to list all animal cares done with animal ID optional filter
    @GetMapping
    public List<AnimalCareResponse> findAll(@RequestParam(required = false) Integer animalId) {
        return service.findAll(animalId);
    }

    // GET method to list all animal cares done in specific animal by ID
    @GetMapping("/animal/{animalId}")
    public List<AnimalCareResponse> findByAnimalId(@PathVariable Integer animalId) {
        return service.findByAnimalId(animalId);
    }

    // GET method to return an animal care by ID
    @GetMapping("/{id}")
    public AnimalCareResponse findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    // POST method to create a new care register
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalCareResponse create(@Valid @RequestBody AnimalCareRequest request) {
        return service.create(request);
    }

    // PUT method to update an existing care register by ID
    @PutMapping("/{id}")
    public AnimalCareResponse update(@PathVariable Integer id, @Valid @RequestBody AnimalCareRequest request) {
        return service.update(id, request);
    }

    // DELETE method to delete an existing care register by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}