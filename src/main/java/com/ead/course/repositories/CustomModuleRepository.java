package com.ead.course.repositories;

import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomModuleRepository {

    List<ModuleModel> findAllModulesIntoCourse(CourseModel course);
    Page<ModuleModel> findAllModulesIntoCourse(CourseModel course, Pageable pageable);

}
