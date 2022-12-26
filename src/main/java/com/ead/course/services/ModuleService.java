package com.ead.course.services;

import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    private final LessonRepository lessonRepo;

    public ModuleService(ModuleRepository moduleRepository, LessonRepository lessonRepo) {
        this.moduleRepository = moduleRepository;
        this.lessonRepo = lessonRepo;
    }

    @Transactional
    public void deleteModule(ModuleModel module) {
        List<LessonModel> lessonList = lessonRepo.findAllLessonsIntoModule(module);
        lessonRepo.deleteAll(lessonList);
        moduleRepository.deleteById(module.getId());
    }
}
