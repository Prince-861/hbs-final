package com.hms.hotel_booking_system.payload;

public class TokenDto {
    private String token;
    private String type;
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
