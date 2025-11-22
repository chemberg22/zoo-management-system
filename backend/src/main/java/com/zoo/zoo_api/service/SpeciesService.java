package com.zoo.zoo_api.service;

import com.zoo.zoo_api.model.Species;
import com.zoo.zoo_api.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeciesService {

    // Link to species repository
    @Autowired
    private SpeciesRepository repository;

    // Return all species
    public List<Species> findAll() {
        return repository.findAll();
    }
}
