package com.zoo.zoo_api.repository;

import com.zoo.zoo_api.model.AnimalCare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnimalCareRepository extends JpaRepository<AnimalCare, Integer> {

    // SELECT from animalCares WHERE animalId = ... ORDER BY RealizationDate DESC
    List<AnimalCare> findByAnimalIdOrderByRealizationDateDesc(Integer animalId);

    // SELECT from careTypes WHERE id = ...
    List<AnimalCare> findByCareTypeId(Integer careTypeId);
}