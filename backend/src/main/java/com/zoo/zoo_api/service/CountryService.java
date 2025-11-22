package com.zoo.zoo_api.service;

import com.zoo.zoo_api.model.Country;
import com.zoo.zoo_api.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository repository;

    public List<Country> findAll() {
        return repository.findAll();
    }
}
