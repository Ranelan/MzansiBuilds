package com.MzansiBuilds.service.imp;

import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.repository.DeveloperRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeveloperServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DeveloperService developerService;

    private Developer developer;

    @BeforeEach
    void setUp() {
        developer = new Developer.DeveloperBuilder()
                .setDeveloperId(1)
                .setUsername("dev1")
                .setEmail("dev1@gmail.com")
                .setPassword("rawPassword")
                .setBio("bio")
                .build();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void create() {
        when(developerRepository.findByUsername("dev1")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(developerRepository.save(any(Developer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Developer created = developerService.create(developer);

        assertNotNull(created);
        assertEquals("dev1", created.getUsername());
        assertEquals("encodedPassword", created.getPassword());

        assertThrows(IllegalArgumentException.class, () -> developerService.create(new Developer.DeveloperBuilder()
                .setUsername("x")
                .setEmail("x@gmail.com")
                .build()));

        when(developerRepository.findByUsername("dev1")).thenReturn(Optional.of(developer));
        assertThrows(IllegalArgumentException.class, () -> developerService.create(developer));

        assertNull(developerService.create(null));
    }

    @Test
    void login() {
        Developer stored = new Developer.DeveloperBuilder()
                .setDeveloperId(1)
                .setUsername("dev1")
                .setEmail("dev1@gmail.com")
                .setPassword("encodedPassword")
                .setBio("bio")
                .build();

        when(developerRepository.findByUsername("dev1")).thenReturn(Optional.of(stored));
        when(passwordEncoder.matches("rawPassword", "encodedPassword")).thenReturn(true);

        Developer loggedIn = developerService.login("dev1", "rawPassword");
        assertNotNull(loggedIn);
        assertEquals("dev1", loggedIn.getUsername());

        when(passwordEncoder.matches("badPassword", "encodedPassword")).thenReturn(false);
        assertThrows(BadCredentialsException.class, () -> developerService.login("dev1", "badPassword"));

        when(developerRepository.findByUsername("unknown")).thenReturn(Optional.empty());
        assertThrows(BadCredentialsException.class, () -> developerService.login("unknown", "rawPassword"));

        assertThrows(IllegalArgumentException.class, () -> developerService.login(null, "pass"));
    }

    @Test
    void logout() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("dev1", null)
        );

        developerService.logout();

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void read() {
        when(developerRepository.findById(1)).thenReturn(Optional.of(developer));
        when(developerRepository.findById(99)).thenReturn(Optional.empty());

        assertNotNull(developerService.read(1));
        assertNull(developerService.read(99));
    }

    @Test
    void update() {
        Developer existing = new Developer.DeveloperBuilder()
                .setDeveloperId(1)
                .setUsername("dev1")
                .setEmail("dev1@gmail.com")
                .setPassword("oldEncoded")
                .setBio("old")
                .build();

        Developer updateRequest = new Developer.DeveloperBuilder()
                .setDeveloperId(1)
                .setUsername("dev1Updated")
                .setEmail("dev1updated@gmail.com")
                .setPassword("newRaw")
                .setBio("new")
                .build();

        when(developerRepository.findById(1)).thenReturn(Optional.of(existing));
        when(passwordEncoder.encode("newRaw")).thenReturn("newEncoded");
        when(developerRepository.save(any(Developer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Developer updated = developerService.update(updateRequest);
        assertNotNull(updated);
        assertEquals("dev1Updated", updated.getUsername());
        assertEquals("newEncoded", updated.getPassword());

        Developer noPasswordUpdate = new Developer.DeveloperBuilder()
                .setDeveloperId(1)
                .setUsername("dev1KeepPass")
                .setEmail("keep@gmail.com")
                .setPassword("")
                .setBio("bio")
                .build();

        Developer updatedNoPassword = developerService.update(noPasswordUpdate);
        assertNotNull(updatedNoPassword);
        assertEquals("oldEncoded", updatedNoPassword.getPassword());

        assertNull(developerService.update(null));
        assertNull(developerService.update(new Developer.DeveloperBuilder().setDeveloperId(0).build()));
    }

    @Test
    void findAll() {
        when(developerRepository.findAll()).thenReturn(List.of(developer));

        List<Developer> developers = developerService.findAll();

        assertEquals(1, developers.size());
        assertEquals("dev1", developers.get(0).getUsername());
    }

    @Test
    void findById() {
        when(developerRepository.findById(1)).thenReturn(Optional.of(developer));
        when(developerRepository.findById(2)).thenReturn(Optional.empty());

        assertTrue(developerService.findById(1).isPresent());
        assertTrue(developerService.findById(2).isEmpty());
    }

    @Test
    void findByUsername() {
        when(developerRepository.findByUsername("dev1")).thenReturn(Optional.of(developer));
        when(developerRepository.findByUsername("nope")).thenReturn(Optional.empty());

        assertTrue(developerService.findByUsername("dev1").isPresent());
        assertTrue(developerService.findByUsername("nope").isEmpty());
    }

    @Test
    void delete() {
        developerService.delete(1);
        verify(developerRepository, times(1)).deleteById(1);
    }
}