package com.ead.course.models;

import com.ead.course.dto.LessonDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@Document(collection = "lessons")
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class LessonModel {

    @Id
    private String id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String videoUrl;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;

    @DocumentReference
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ModuleModel module;

    public LessonModel(LessonDto lessonDto) {
        this.title = lessonDto.getTitle();
        this.description = lessonDto.getDescription();
        this.videoUrl = lessonDto.getVideoUrl();
        this.createdAt = LocalDateTime.now();
    }
}
