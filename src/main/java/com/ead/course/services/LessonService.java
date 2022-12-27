package com.ead.course.services;

import com.ead.course.dto.LessonDto;
import com.ead.course.exceptions.ResourceNotFoundException;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LessonService {

    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    private static final String LESSON_NOT_FOUND = "Lesson not found";
    private static final String LESSON_MODULE_NOT_FOUND = "Lesson not found in this module";

    public LessonService(ModuleRepository moduleRepository, LessonRepository lessonRepository) {
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    public LessonModel createLesson(String moduleId, LessonDto lessonDto) {
        ModuleModel module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(LESSON_NOT_FOUND));
        LessonModel lesson = new LessonModel(lessonDto);
        lesson.setModule(module);
        return lessonRepository.save(lesson);
    }

    public void deleteLesson(String moduleId, String lessonId) {
        LessonModel lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(LESSON_NOT_FOUND));
        if (lesson.getModule().getId().equals(moduleId)) {
            lessonRepository.delete(lesson);
        } else {
            throw new ResourceNotFoundException(LESSON_MODULE_NOT_FOUND);
        }
    }

    public LessonModel updateLesson(String moduleId, String lessonId, LessonDto lessonDto) {
        LessonModel lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(LESSON_NOT_FOUND));
        if (lesson.getModule().getId().equals(moduleId)) {
            lesson.setTitle(lessonDto.getTitle());
            lesson.setDescription(lessonDto.getDescription());
            lesson.setVideoUrl(lessonDto.getVideoUrl());
            return lessonRepository.save(lesson);
        } else {
            throw new ResourceNotFoundException(LESSON_MODULE_NOT_FOUND);
        }
    }

    public LessonModel getLesson(String moduleId, String lessonId) {
        LessonModel lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(LESSON_NOT_FOUND));
        if (lesson.getModule().getId().equals(moduleId)) {
            return lesson;
        } else {
            throw new ResourceNotFoundException(LESSON_MODULE_NOT_FOUND);
        }
    }

    public Page<LessonModel> getLessons(String moduleId, Pageable pageable) {
        ModuleModel module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found"));

        return lessonRepository.findAllLessonsIntoModule(module, pageable);
    }
}
