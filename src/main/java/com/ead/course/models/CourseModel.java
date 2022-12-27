package com.ead.course.models;

import com.ead.course.dto.CourseDto;
import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Document(collection = "courses")
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class CourseModel {

    @Id
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private String imageUrl;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
    @NotNull
    private CourseStatus courseStatus;
    @NotNull
    private CourseLevel courseLevel;
    @NotNull
    private UUID userInstuctor;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{ 'course': ?#{#self._id} }")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<ModuleModel> modules;

    public CourseModel(CourseDto courseDto) {
        this.name = courseDto.getName();
        this.description = courseDto.getDescription();
        this.imageUrl = courseDto.getImageUrl();
        this.courseStatus = courseDto.getCourseStatus();
        this.userInstuctor = courseDto.getUserInstructor();
        this.courseLevel = courseDto.getCourseLevel();
    }
}
