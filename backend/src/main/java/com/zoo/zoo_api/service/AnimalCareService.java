package com.zoo.zoo_api.service;

import com.zoo.zoo_api.dto.*;
import com.zoo.zoo_api.model.*;
import com.zoo.zoo_api.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AnimalCareService {

    // Link for animal care, animal and care type repository
    private final AnimalCareRepository repository;
    private final AnimalRepository animalRepository;
    private final CareTypeRepository careTypeRepository;

    // Imutable
    public AnimalCareService(AnimalCareRepository repository,
                             AnimalRepository animalRepository,
                             CareTypeRepository careTypeRepository) {
        this.repository = repository;
        this.animalRepository = animalRepository;
        this.careTypeRepository = careTypeRepository;
    }

    // Return all animal cares or by animal ID. Entities > map to DTO > to List
    public List<AnimalCareResponse> findAll(Integer animalId) {
        List<AnimalCare> cares = (animalId != null)
                ? repository.findByAnimalIdOrderByRealizationDateDesc(animalId)
                : repository.findAll();
        return cares.stream().map(this::toResponse).toList();
    }

    // Return all cares by animal ID. Entities > map to DTO > to List
    public List<AnimalCareResponse> findByAnimalId(Integer animalId) {
        return repository.findByAnimalIdOrderByRealizationDateDesc(animalId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Return an animal care by ID
    public AnimalCareResponse findById(Integer id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Care performed not found: " + id));
    }

    // Creates an animal using Lombok builder, persists in database and return the DTO response. Guarantee a realization date
    @Transactional
    public AnimalCareResponse create(AnimalCareRequest request) {
        AnimalCare care = AnimalCare.builder()
                .animal(getAnimal(request.animalId()))
                .careType(getCareType(request.careTypeId()))
                .realizationDate(request.realizationDate() != null ? request.realizationDate() : LocalDate.now())
                .observations(request.observations())
                .build();
        return toResponse(repository.save(care));
    }

    // Update an animal care verifying its existence by ID, persists in database and return the DTO response
    @Transactional
    public AnimalCareResponse update(Integer id, AnimalCareRequest request) {
        AnimalCare care = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Care performed not found: " + id));
        care.setAnimal(getAnimal(request.animalId()));
        care.setCareType(getCareType(request.careTypeId()));
        care.setRealizationDate(request.realizationDate());
        care.setObservations(request.observations());
        return toResponse(repository.save(care));
    }

    // Delete an existing animal care verifying its existence by ID and update the database
    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Care performed not found: " + id);
        }
        repository.deleteById(id);
    }

    // Entity to DTO
    private AnimalCareResponse toResponse(AnimalCare ac) {
        return new AnimalCareResponse(
                ac.getId(),
                ac.getRealizationDate(),
                ac.getObservations(),
                new AnimalSimpleResponse(
                        ac.getAnimal().getId(),
                        ac.getAnimal().getName()
                ),
                new CareTypeResponse(
                        ac.getCareType().getId(),
                        ac.getCareType().getName(),
                        ac.getCareType().getDescription(),
                        ac.getCareType().getFrequency()
                )
        );
    }

    // Guarantee the animal existence by ID
    private Animal getAnimal(Integer id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Animal not found: " + id));
    }

    // Guarantee the care type existence by ID
    private CareType getCareType(Integer id) {
        return careTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Care type not found: " + id));
    }
}