package com.ead.course.controllers;

import com.ead.course.dto.LessonDto;
import com.ead.course.models.LessonModel;
import com.ead.course.services.LessonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<LessonModel> createLesson(@PathVariable String moduleId,
                                                    @RequestBody @Valid LessonDto lessonDto) {
        return ResponseEntity.ok(lessonService.createLesson(moduleId, lessonDto));
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<HttpStatus> deleteLesson(@PathVariable String moduleId,
                                                   @PathVariable String lessonId) {
        lessonService.deleteLesson(moduleId, lessonId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<LessonModel> updateLesson(@PathVariable String moduleId,
                                                    @PathVariable String lessonId,
                                                    @RequestBody @Valid LessonDto lessonDto) {
        return ResponseEntity.ok(lessonService.updateLesson(moduleId, lessonId, lessonDto));
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<LessonModel> getLesson(@PathVariable String moduleId,
                                                 @PathVariable String lessonId) {
        return ResponseEntity.ok(lessonService.getLesson(moduleId, lessonId));
    }

    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Set<LessonModel>> getLessons(@PathVariable String moduleId) {
        return ResponseEntity.ok(lessonService.getLessons(moduleId));
    }
}
