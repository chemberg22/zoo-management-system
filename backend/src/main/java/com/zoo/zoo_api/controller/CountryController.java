package com.zoo.zoo_api.controller;

import com.zoo.zoo_api.model.Country;
import com.zoo.zoo_api.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Country>> findAllCountries() {
        List<Country> country = service.findAll();
        return ResponseEntity.ok(country);
    }
}
