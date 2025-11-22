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

    private final AnimalRepository animalRepository;
    private final SpeciesRepository speciesRepository;
    private final HabitatRepository habitatRepository;
    private final CountryRepository countryRepository;

    public AnimalService(AnimalRepository animalRepository,
                         SpeciesRepository speciesRepository,
                         HabitatRepository habitatRepository,
                         CountryRepository countryRepository) {
        this.animalRepository = animalRepository;
        this.speciesRepository = speciesRepository;
        this.habitatRepository = habitatRepository;
        this.countryRepository = countryRepository;
    }

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

    public AnimalResponse findById(Integer id) {
        return animalRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Animal not found: " + id));
    }

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

    @Transactional
    public void delete(Integer id) {
        if (!animalRepository.existsById(id)) {
            throw new EntityNotFoundException("Animal not found: " + id);
        }
        animalRepository.deleteById(id);
    }

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

    private Species getSpecies(Integer id) {
        return speciesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Species not found: " + id));
    }

    private Habitat getHabitat(Integer id) {
        return habitatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Habitat not found: " + id));
    }

    private Country getCountry(Integer id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country not found: " + id));
    }
}