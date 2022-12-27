package com.ead.course.models;

import com.ead.course.dto.ModuleDto;
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

@Data
@Document(collection = "modules")
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ModuleModel {

    @Id
    private String id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @DocumentReference
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CourseModel course;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{ 'module': ?#{#self._id} }", lazy = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<LessonModel> lessons;

    public ModuleModel(ModuleDto moduleDtoDto) {
        this.title = moduleDtoDto.getTitle();
        this.description = moduleDtoDto.getDescription();
        this.createdAt = LocalDateTime.now();
    }
}
