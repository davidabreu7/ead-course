package com.ead.course.dto;

import java.time.ZonedDateTime;

public record UserRecord(String id, String username, String email, String fullname,
                         String phoneNumber, String cpf, ZonedDateTime createdAt, ZonedDateTime updatedAt,
                         String userStatus, String userType) {
}

