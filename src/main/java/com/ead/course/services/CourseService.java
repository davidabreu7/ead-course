package com.ead.course.services;

import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import org.springframework.stereotype.Service;

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

    public void deleteCourse(CourseModel course) {
        List<ModuleModel> modules = moduleRepository.findAllModulesIntoCourse(course);
        modules.forEach(module -> {
            List<LessonModel> lessonList = lessonRepository.findAllLessonsIntoModule(module);
            lessonRepository.deleteAll(lessonList);
        });
    }
}
