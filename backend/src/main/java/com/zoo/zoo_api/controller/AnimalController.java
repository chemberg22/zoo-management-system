package com.zoo.zoo_api.controller;

import com.zoo.zoo_api.dto.*;
import com.zoo.zoo_api.service.AnimalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    // Link to animal service
    private final AnimalService service;

    // Immutable
    public AnimalController(AnimalService service) {
        this.service = service;
    }

    // GET method to list animal by optional filters
    @GetMapping
    public List<AnimalResponse> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer speciesId,
            @RequestParam(required = false) Integer habitatId,
            @RequestParam(required = false) Integer countryId) {
        return service.findAll(name, speciesId, habitatId, countryId);
    }

    // GET method to return a animal by ID
    @GetMapping("/{id}")
    public AnimalResponse findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    // POST method to create a new animal
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalResponse create(@Valid @RequestBody AnimalRequest request) {
        return service.create(request);
    }

    // PUT method to update and existing animal by ID
    @PutMapping("/{id}")
    public AnimalResponse update(@PathVariable Integer id, @Valid @RequestBody AnimalRequest request) {
        return service.update(id, request);
    }

    // DELETE method to update and existing animal by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}