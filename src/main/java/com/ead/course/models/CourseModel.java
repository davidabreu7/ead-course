package com.ead.course.models;

import com.ead.course.dto.CourseDto;
import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;
    @NotNull
    private CourseStatus courseStatus;
    @NotNull
    private CourseLevel courseLevel;
    @NotNull
    private UUID userInstuctor;

    @ReadOnlyProperty
    @DocumentReference(collection = "modules", lookup = "{ 'course': ?#{#self._id} }", lazy = true)
    @JsonIgnore
    private List<ModuleModel> modules = new ArrayList<>();

    @JsonIgnore
    private List<String> users = new ArrayList<>();

    public CourseModel(CourseDto courseDto) {
        this.name = courseDto.getName();
        this.description = courseDto.getDescription();
        this.imageUrl = courseDto.getImageUrl();
        this.courseStatus = courseDto.getCourseStatus();
        this.userInstuctor = courseDto.getUserInstructor();
        this.courseLevel = courseDto.getCourseLevel();
    }
}
