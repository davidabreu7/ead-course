package com.ead.course.services;

import com.ead.course.dto.CourseDto;
import com.ead.course.exceptions.ResourceNotFoundException;
import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final ModuleRepository moduleRepository;

    private final LessonRepository lessonRepository;

    public CourseService(CourseRepository courseRepository, ModuleRepository moduleRepository, LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    public void deleteCourse(String id) {
        CourseModel course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        List<ModuleModel> modules = moduleRepository.findAllModulesIntoCourse(course);
        modules.forEach(module -> {
            List<LessonModel> lessonList = lessonRepository.findAllLessonsIntoModule(module);
            lessonRepository.deleteAll(lessonList);
        });
        moduleRepository.deleteAll(modules);
        courseRepository.delete(course);
    }

    public CourseModel createCourse(CourseDto courseDto) {
        CourseModel course = new CourseModel(courseDto);

        course.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        course.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        return courseRepository.save(course);
    }

    public CourseModel updateCourse(String id, CourseDto courseDto) {
        CourseModel course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setImageUrl(courseDto.getImageUrl());
        course.setCourseStatus(courseDto.getCourseStatus());
        course.setUserInstuctor(courseDto.getUserInstructor());
        course.setCourseLevel(courseDto.getCourseLevel());
        course.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        return courseRepository.save(course);
    }

    public List<CourseModel> getAllCourses() {
        return courseRepository.findAll();
    }

    public CourseModel getCourseById(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }
}
