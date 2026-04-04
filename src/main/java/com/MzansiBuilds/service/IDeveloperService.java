package com.MzansiBuilds.service;

import com.MzansiBuilds.domain.Developer;

import java.util.List;
import java.util.Optional;

public interface IDeveloperService extends IService<Developer, Integer>{

    List<Developer> findAll();
    Optional<Developer> findById(Integer id);
    Optional<Developer> findByUsername(String username);
    Developer login(String username, String password);
    void logout();
}