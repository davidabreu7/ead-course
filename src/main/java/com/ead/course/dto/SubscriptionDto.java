package com.ead.course.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubscriptionDto {

   @NotNull
    private String userId;
   private String userName;
   private String courseId;
   private String courseName;
   private String message;
}
