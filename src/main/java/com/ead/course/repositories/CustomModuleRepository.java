package com.ead.course.repositories;

import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;

import java.util.List;

public interface CustomModuleRepository {

    public List<ModuleModel> findAllModulesIntoCourse(CourseModel course);
}
