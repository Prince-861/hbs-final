package com.hms.hotel_booking_system.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ErrorDto {
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp; // Changed from Date to LocalDateTime

    private String uri;

    public ErrorDto(String message, LocalDateTime timestamp, String uri) {
        this.message = message;
        this.timestamp = timestamp;
        this.uri = uri;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUri() {
        return uri;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
