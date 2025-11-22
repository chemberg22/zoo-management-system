package com.zoo.zoo_api.controller;

import com.zoo.zoo_api.dto.*;
import com.zoo.zoo_api.service.CareTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/care-types")
public class CareTypeController {

    // Link to care type service
    private final CareTypeService service;

    // Imutable
    public CareTypeController(CareTypeService service) {
        this.service = service;
    }

    // GET method to list all care types
    @GetMapping
    public List<CareTypeResponse> findAll() {
        return service.findAll();
    }

    // GET method to return a care type by ID
    @GetMapping("/{id}")
    public CareTypeResponse findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    // POST method to create a new care type
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CareTypeResponse create(@Valid @RequestBody CareTypeRequest request) {
        return service.create(request);
    }

    // PUT method to update an existing care type by ID
    @PutMapping("/{id}")
    public CareTypeResponse update(@PathVariable Integer id, @Valid @RequestBody CareTypeRequest request) {
        return service.update(id, request);
    }

    // DELETE method to delete an existing care type by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}