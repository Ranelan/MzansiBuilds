package com.MzansiBuilds.service.imp;

import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.repository.DeveloperRepository;
import com.MzansiBuilds.service.IDeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeveloperService implements IDeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Developer create(Developer developer) {
       if (developer == null){
           return null;
       }
       if (developer.getUsername() == null || developer.getPassword() == null || developer.getEmail() == null) {
              throw new IllegalArgumentException("Username, password, and email must not be null");
       }
       if (developerRepository.findByUsername(developer.getUsername()).isPresent()) {
              throw new IllegalArgumentException("Username already exists");
       }
         String encodedPassword = passwordEncoder.encode(developer.getPassword());
            Developer newDeveloper = new Developer.DeveloperBuilder()
                    .setUsername(developer.getUsername())
                    .setEmail(developer.getEmail())
                    .setPassword(encodedPassword)
                    .setBio(developer.getBio())
//                    .setCreatedAt(LocalDateTime.now())
//                    .setUpdatedAt(LocalDateTime.now())
                    .build();

         return developerRepository.save(newDeveloper);
    }

    @Override
    public Developer login(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and password must not be null");
        }

        Developer developer = developerRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(password, developer.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return developer;
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    @Override
    public Developer read(Integer integer) {
        return developerRepository.findById(integer).orElse(null);
    }

    @Override
    public Developer update(Developer developer) {
        if(developer == null || developer.getDeveloperId() == 0){
            return null;
        }
        Optional<Developer> existingDeveloper = developerRepository.findById(developer.getDeveloperId());
        if(existingDeveloper.isPresent()) {
            String passwordToStore = existingDeveloper.get().getPassword();
            if (developer.getPassword() != null && !developer.getPassword().isBlank()) {
                passwordToStore = passwordEncoder.encode(developer.getPassword());
            }

            Developer updateDev = new Developer.DeveloperBuilder().copy(existingDeveloper.get())
                    .setUsername(developer.getUsername())
                    .setEmail(developer.getEmail())
                    .setPassword(passwordToStore)
                    .setBio(developer.getBio())
                    .build();
            return developerRepository.save(updateDev);
        }
        return null;
    }


    @Override
    public List<Developer> findAll() {
        return developerRepository.findAll();
    }

    @Override
    public Optional<Developer> findById(Integer id) {
        return developerRepository.findById(id);
    }

    @Override
    public Optional<Developer> findByUsername(String username) {
        return developerRepository.findByUsername(username);
    }


    @Override
    public void delete(Integer id) {
        developerRepository.deleteById(id);
    }
}