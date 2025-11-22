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

    private final AnimalService service;

    public AnimalController(AnimalService service) {
        this.service = service;
    }

    @GetMapping
    public List<AnimalResponse> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer speciesId,
            @RequestParam(required = false) Integer habitatId,
            @RequestParam(required = false) Integer countryId) {
        return service.findAll(name, speciesId, habitatId, countryId);
    }

    @GetMapping("/{id}")
    public AnimalResponse findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalResponse create(@Valid @RequestBody AnimalRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public AnimalResponse update(@PathVariable Integer id, @Valid @RequestBody AnimalRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}