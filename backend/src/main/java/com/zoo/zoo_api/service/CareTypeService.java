package com.zoo.zoo_api.service;

import com.zoo.zoo_api.dto.*;
import com.zoo.zoo_api.model.CareType;
import com.zoo.zoo_api.repository.CareTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CareTypeService {

    // Link for care types repository
    private final CareTypeRepository repository;

    // Imutable
    public CareTypeService(CareTypeRepository repository) {
        this.repository = repository;
    }

    // Return all care types. Entities > map to DTO > to List
    public List<CareTypeResponse> findAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    // Return care type by ID. If exists > map to Response
    public CareTypeResponse findById(Integer id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Care type not found: " + id));
    }

    // Creates a new care type using Lombok builder, persists in database and return the DTO response
    @Transactional
    public CareTypeResponse create(CareTypeRequest request) {
        CareType careType = CareType.builder()
                .name(request.name())
                .description(request.description())
                .frequency(request.frequency())
                .build();
        return toResponse(repository.save(careType));
    }

    // Update an existing care type verifying its existence by ID, persists in database and return the DTO response
    @Transactional
    public CareTypeResponse update(Integer id, CareTypeRequest request) {
        CareType careType = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Care type not found: " + id));
        careType.setName(request.name());
        careType.setDescription(request.description());
        careType.setFrequency(request.frequency());

        return toResponse(repository.save(careType));
    }

    // Delete an existing care type verifying its existence by ID and update the database
    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Care type not found: " + id);
        }
        repository.deleteById(id);
    }

    // Entity > DTO
    private CareTypeResponse toResponse(CareType c) {
        return new CareTypeResponse(
                c.getId(),
                c.getName(),
                c.getDescription(),
                c.getFrequency());
    }
}