package com.ead.course.dto;

import java.time.LocalDateTime;

public record UserRecord(String id, String username, String email, String fullname,
                         String phoneNumber, String cpf, LocalDateTime createdAt, LocalDateTime updatedAt,
                         String userStatus, String userType) {
}

