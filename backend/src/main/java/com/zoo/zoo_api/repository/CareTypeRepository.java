package com.zoo.zoo_api.repository;

import com.zoo.zoo_api.model.CareType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareTypeRepository extends JpaRepository<CareType, Integer> {
}