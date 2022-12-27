package com.ead.course.services;

import com.ead.course.dto.ModuleDto;
import com.ead.course.exceptions.ResourceNotFoundException;
import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final  CourseRepository courseRepository;

    private static final String MODULE_NOT_FOUND = "Module not found";
    private static final String MODULE_COURSE_NOT_FOUND = "Module not found in this course";
    private static final String COURSE_NOT_FOUND = "Course not found";

    public ModuleService(ModuleRepository moduleRepository, LessonRepository lessonRepository, CourseRepository courseRepository) {
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public void deleteModule(ModuleModel module) {
        List<LessonModel> lessonList = lessonRepository.findAllLessonsIntoModule(module);
        lessonRepository.deleteAll(lessonList);
        moduleRepository.deleteById(module.getId());
    }

    @Transactional
    public void deleteModuleController(String courseId, String moduleId) {
        ModuleModel module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(MODULE_NOT_FOUND));
        if (module.getCourse().getId().equals(courseId)) {
            deleteModule(module);
        } else {
            throw new ResourceNotFoundException(MODULE_COURSE_NOT_FOUND);
        }
    }

    public ModuleModel createModule(String courseId, ModuleDto moduleDto) {
        ModuleModel module = new ModuleModel(moduleDto);
        CourseModel course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(COURSE_NOT_FOUND));
        module.setCourse(course);
        return moduleRepository.save(module);
    }

    public ModuleModel updateModule(String courseId, String moduleId, ModuleDto moduleDto) {
        ModuleModel module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(MODULE_NOT_FOUND));
        if (module.getCourse().getId().equals(courseId)) {
            module.setTitle(moduleDto.getTitle());
            module.setDescription(moduleDto.getDescription());
            return moduleRepository.save(module);
        } else {
            throw new ResourceNotFoundException(MODULE_COURSE_NOT_FOUND);
        }
    }

    public Page<ModuleModel> getAllModules(String courseId, Pageable pageable) {
        CourseModel course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(COURSE_NOT_FOUND));
        return moduleRepository.findAllModulesIntoCourse(course, pageable);
    }

    public ModuleModel getModuleById(String courseId, String moduleId) {
        ModuleModel module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(MODULE_NOT_FOUND));

        if (module.getCourse().getId().equals(courseId)) {
            return module;
        } else {
            throw new ResourceNotFoundException(MODULE_COURSE_NOT_FOUND);
        }
    }
}
