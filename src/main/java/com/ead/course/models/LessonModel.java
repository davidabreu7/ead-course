package com.ead.course.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@Document(collection = "lessons")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonModel {

    @Id
    private String id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String videoUrl;
    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @DocumentReference(lazy = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ModuleModel module;
}
