package com.ead.course.repositories;

import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;

import java.util.List;

public interface CustomLessonRepository {

    List<LessonModel> findAllLessonsIntoModule(ModuleModel module);
}
