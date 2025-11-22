package com.zoo.zoo_api.repository;

import com.zoo.zoo_api.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    // SELECT from species WHERE id = ...
    List<Animal> findBySpeciesId(Integer speciesId);

    // SELECT from habitats WHERE id = ...
    List<Animal> findByHabitatId(Integer habitatId);

    // SELECT from countries WHERE id = ...
    List<Animal> findByBirthPlaceId(Integer countryId);

    // SELECT * FROM animals WHERE LOWER(name) LIKE LOWER('%<name>%');
    List<Animal> findByNameContainingIgnoreCase(String name);

    // Filter by partial name + species filter + species filter
    @Query("SELECT a FROM Animal a WHERE " +
            "(:name IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:speciesId IS NULL OR a.species.id = :speciesId) AND " +
            "(:habitatId IS NULL OR a.habitat.id = :habitatId)")
    List<Animal> search(String name, Integer speciesId, Integer habitatId);
}