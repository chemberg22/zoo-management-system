package com.zoo.zoo_api.service;

import com.zoo.zoo_api.model.Habitat;
import com.zoo.zoo_api.repository.HabitatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitatService {

    // Link to habitats repository
    @Autowired
    private HabitatRepository repository;

    // Return all habitats
    public List<Habitat> findAll() {
        return repository.findAll();
    }
}
