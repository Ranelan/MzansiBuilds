package com.MzansiBuilds.util;

import java.time.LocalDateTime;

public class Helper {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isValidUsername(String username) {
        return username != null && username.length() >= 3;
    }

    public static boolean isValidTimeStamp(LocalDateTime timestamp) {
        return timestamp != null && timestamp.isBefore(LocalDateTime.now());
    }


}
