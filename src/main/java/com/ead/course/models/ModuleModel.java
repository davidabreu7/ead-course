package com.ead.course.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Document(collection = "modules")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleModel {

    @Id
    private String id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @DocumentReference(lazy = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CourseModel course;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{ 'module': ?#{#self._id} }")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<LessonModel> lessons;
}
