package com.hms.hotel_booking_system;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class AdminPassword {
    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("password",BCrypt.gensalt(5)));
    }
}
