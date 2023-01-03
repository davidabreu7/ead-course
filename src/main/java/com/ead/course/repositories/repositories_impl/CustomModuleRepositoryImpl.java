package com.ead.course.repositories.repositories_impl;

import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CustomModuleRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class CustomModuleRepositoryImpl implements CustomModuleRepository {
    private final MongoTemplate mongoTemplate;

    public CustomModuleRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<ModuleModel> findAllModulesIntoCourse(CourseModel course) {
        Query query = new Query();
        query.addCriteria(Criteria.where("course").is(new ObjectId(course.getId())));
        return mongoTemplate.find(query, ModuleModel.class);
    }

    @Override
    public Page<ModuleModel> findAllModulesIntoCourse(CourseModel course, Pageable pageable) {
        List<ModuleModel> moduleModels = course.getModules();
        return new PageImpl<>(moduleModels, pageable, moduleModels.size());
    }


}

