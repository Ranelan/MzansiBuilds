package com.MzansiBuilds.factory;

import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.util.Helper;

import java.time.LocalDateTime;

public class DeveloperFactory {

    public static Developer createDeveloper(String username, String email, String password, String bio, LocalDateTime createdAt, LocalDateTime updatedAt) {
        if(!Helper.isValidUsername(username) ||
              !Helper.isValidEmail(email) ||
              !Helper.isValidPassword(password) ||
              !Helper.isValidTimeStamp(createdAt) ||
              !Helper.isValidTimeStamp(updatedAt)) {
            return null;
        }

        return new Developer.DeveloperBuilder()
                .setUsername(username)
                .setEmail(email)
                .setPassword(password)
                .setBio(bio)
                .setUpdated_at(updatedAt)
                .build();
    }
}
