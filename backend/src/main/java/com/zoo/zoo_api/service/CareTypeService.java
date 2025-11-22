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

    private final CareTypeRepository repository;

    public CareTypeService(CareTypeRepository repository) {
        this.repository = repository;
    }

    public List<CareTypeResponse> findAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public CareTypeResponse findById(Integer id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Care type not found: " + id));
    }

    @Transactional
    public CareTypeResponse create(CareTypeRequest request) {
        CareType careType = CareType.builder()
                .name(request.name())
                .description(request.description())
                .frequency(request.frequency())
                .build();
        return toResponse(repository.save(careType));
    }

    @Transactional
    public CareTypeResponse update(Integer id, CareTypeRequest request) {
        CareType careType = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Care type not found: " + id));
        careType.setName(request.name());
        careType.setDescription(request.description());
        careType.setFrequency(request.frequency());
        return toResponse(repository.save(careType));
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Care type not found: " + id);
        }
        repository.deleteById(id);
    }

    private CareTypeResponse toResponse(CareType c) {
        return new CareTypeResponse(c.getId(), c.getName(), c.getDescription(), c.getFrequency());
    }
}