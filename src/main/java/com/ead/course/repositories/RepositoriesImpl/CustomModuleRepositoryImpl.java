package com.ead.course.repositories.customRepositoriesImpl;

import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CustomModuleRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class CustomModuleRepositoryImpl implements CustomModuleRepository {
    private final MongoTemplate mongoTemplate;

    public CustomModuleRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public java.util.List<ModuleModel> findAllModulesIntoCourse(CourseModel course) {
       Query query = new Query();
        query.addCriteria(Criteria.where("course").is(course.getId()));
        return mongoTemplate.find(query, ModuleModel.class);
    }
}

