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

    private final CareTypeService service;

    public CareTypeController(CareTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<CareTypeResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CareTypeResponse findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CareTypeResponse create(@Valid @RequestBody CareTypeRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public CareTypeResponse update(@PathVariable Integer id, @Valid @RequestBody CareTypeRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}