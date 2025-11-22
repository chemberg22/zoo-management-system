package com.zoo.zoo_api.service;

import com.zoo.zoo_api.dto.*;
import com.zoo.zoo_api.model.*;
import com.zoo.zoo_api.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AnimalService {

    // Link for animal, species, habitat and country repository
    private final AnimalRepository animalRepository;
    private final SpeciesRepository speciesRepository;
    private final HabitatRepository habitatRepository;
    private final CountryRepository countryRepository;

    // Imutable
    public AnimalService(AnimalRepository animalRepository,
                         SpeciesRepository speciesRepository,
                         HabitatRepository habitatRepository,
                         CountryRepository countryRepository) {
        this.animalRepository = animalRepository;
        this.speciesRepository = speciesRepository;
        this.habitatRepository = habitatRepository;
        this.countryRepository = countryRepository;
    }

    // Return all animals with optional filters. Entities > map to DTO > to List
    public List<AnimalResponse> findAll(String name, Integer speciesId, Integer habitatId, Integer countryId) {
        List<Animal> animals;

        if (name != null && !name.isBlank()) {
            animals = animalRepository.findByNameContainingIgnoreCase(name);
        } else if (speciesId != null) {
            animals = animalRepository.findBySpeciesId(speciesId);
        } else if (habitatId != null) {
            animals = animalRepository.findByHabitatId(habitatId);
        } else if (countryId != null) {
            animals = animalRepository.findByBirthPlaceId(countryId);
        } else {
            animals = animalRepository.findAll();
        }

        return animals.stream().map(this::toResponse).toList();
    }

    // Return animal by ID. If exists > map to Response
    public AnimalResponse findById(Integer id) {
        return animalRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Animal not found: " + id));
    }

    // Creates an animal using Lombok builder, persists in database and return the DTO response
    @Transactional
    public AnimalResponse create(AnimalRequest request) {
        Animal animal = Animal.builder()
                .name(request.name())
                .description(request.description())
                .birthDate(request.birthDate())
                .species(getSpecies(request.speciesId()))
                .habitat(getHabitat(request.habitatId()))
                .birthPlace(request.birthPlaceId() != null ? getCountry(request.birthPlaceId()) : null)
                .build();

        return toResponse(animalRepository.save(animal));
    }

    // Update an existing animal verifying its existence by ID, persists in database and return the DTO response
    @Transactional
    public AnimalResponse update(Integer id, AnimalRequest request) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Animal not found: " + id));
        animal.setName(request.name());
        animal.setDescription(request.description());
        animal.setBirthDate(request.birthDate());
        animal.setSpecies(getSpecies(request.speciesId()));
        animal.setHabitat(getHabitat(request.habitatId()));
        animal.setBirthPlace(request.birthPlaceId() != null ? getCountry(request.birthPlaceId()) : null);

        return toResponse(animalRepository.save(animal));
    }

    // Delete an existing animal verifying its existence by ID and update the database
    @Transactional
    public void delete(Integer id) {
        if (!animalRepository.existsById(id)) {
            throw new EntityNotFoundException("Animal not found: " + id);
        }
        animalRepository.deleteById(id);
    }

    // Entity to DTO
    private AnimalResponse toResponse(Animal a) {
        CountryResponse countryResponse = null;
        if (a.getBirthPlace() != null) {
            countryResponse = new CountryResponse(
                    a.getBirthPlace().getId(),
                    a.getBirthPlace().getName()
            );
        }

        return new AnimalResponse(
                a.getId(),
                a.getName(),
                a.getDescription(),
                a.getBirthDate(),
                a.getRegistrationDate(),
                new SpeciesResponse(a.getSpecies().getId(), a.getSpecies().getName()),
                new HabitatResponse(a.getHabitat().getId(), a.getHabitat().getName(), a.getHabitat().getDescription()),
                countryResponse
        );
    }

    // Aux method to valid species FK
    private Species getSpecies(Integer id) {
        return speciesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Species not found: " + id));
    }

    // Aux method to valid habitat FK
    private Habitat getHabitat(Integer id) {
        return habitatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Habitat not found: " + id));
    }

    // Aux method to valid country FK
    private Country getCountry(Integer id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country not found: " + id));
    }
}