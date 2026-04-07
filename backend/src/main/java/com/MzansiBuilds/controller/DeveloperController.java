package com.MzansiBuilds.controller;

import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.dto.DeveloperCreateRequestDto;
import com.MzansiBuilds.dto.DeveloperResponseDto;
import com.MzansiBuilds.dto.DeveloperUpdateRequestDto;
import com.MzansiBuilds.dto.LoginRequestDto;
import com.MzansiBuilds.service.imp.DeveloperService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/developer")
@Validated
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    @PostMapping("/register")
    public ResponseEntity<DeveloperResponseDto> registerDeveloper(@Valid @RequestBody DeveloperCreateRequestDto developer) {
        Developer developerToCreate = new Developer.DeveloperBuilder()
                .setUsername(developer.username())
                .setEmail(developer.email())
                .setPassword(developer.password())
                .setBio(developer.bio())
                .build();

        Developer newDeveloper = developerService.create(developerToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDto(newDeveloper));
    }

    @PostMapping("/login")
    public ResponseEntity<DeveloperResponseDto> loginDeveloper(@Valid @RequestBody LoginRequestDto loginRequest) {
        try {
            Developer developer = developerService.login(loginRequest.username(), loginRequest.password());
            return ResponseEntity.ok(toResponseDto(developer));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutDeveloper() {
        developerService.logout();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<DeveloperResponseDto> getDeveloperById(@PathVariable @Positive Integer id) {
        return developerService.findById(id)
                .map(this::toResponseDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<DeveloperResponseDto>> getAllDevelopers() {
        List<DeveloperResponseDto> developers = developerService.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
        return ResponseEntity.ok(developers);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<DeveloperResponseDto> getDeveloperByUsername(@PathVariable @NotBlank String username) {
        return developerService.findByUsername(username)
                .map(this::toResponseDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/updateDeveloper/{id}")
    public ResponseEntity<DeveloperResponseDto> updateDeveloper(@PathVariable @Positive Integer id,
                                                                @Valid @RequestBody DeveloperUpdateRequestDto updateRequest) {
        Developer existing = developerService.read(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        Developer developerToUpdate = new Developer.DeveloperBuilder()
                .copy(existing)
                .setUsername(updateRequest.username() != null ? updateRequest.username() : existing.getUsername())
                .setEmail(updateRequest.email() != null ? updateRequest.email() : existing.getEmail())
                .setPassword(updateRequest.password())
                .setBio(updateRequest.bio() != null ? updateRequest.bio() : existing.getBio())
                .build();

        Developer updatedDeveloper = developerService.update(developerToUpdate);
        return ResponseEntity.ok(toResponseDto(updatedDeveloper));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable @Positive Integer id) {
        if (developerService.read(id) == null) {
            return ResponseEntity.notFound().build();
        }
        developerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //AI used to generate this method, it converts a CollaborationRequest entity to a CollaborationRequestResponseDto
    private DeveloperResponseDto toResponseDto(Developer developer) {
        return new DeveloperResponseDto(
                developer.getDeveloperId(),
                developer.getUsername(),
                developer.getEmail(),
                developer.getBio(),
                developer.getCreatedAt(),
                developer.getUpdatedAt()
        );
    }
}
