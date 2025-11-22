package com.zoo.zoo_api.repository;

import com.zoo.zoo_api.model.Habitat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitatRepository extends JpaRepository<Habitat, Integer> {
}