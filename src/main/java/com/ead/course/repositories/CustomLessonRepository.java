package com.ead.course.repositories;

import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomLessonRepository {

    List<LessonModel> findAllLessonsIntoModule(ModuleModel module);

    Page<LessonModel> findAllLessonsIntoModule(ModuleModel module, Pageable pageable);
}
