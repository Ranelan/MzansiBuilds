package com.MzansiBuilds.repository;

import com.MzansiBuilds.domain.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<Developer,Integer> {

    Optional<Developer> findByUsername(String name);
    Optional<Developer> findByEmail(String email);
}
