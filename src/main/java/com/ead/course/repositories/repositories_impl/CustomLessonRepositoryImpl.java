package com.ead.course.repositories.repositories_impl;

import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CustomLessonRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class CustomLessonRepositoryImpl implements CustomLessonRepository {

    private final MongoTemplate mongoTemplate;

    public CustomLessonRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<LessonModel> findAllLessonsIntoModule(ModuleModel module) {
        Query query = new Query();
        query.addCriteria(Criteria.where("module").is(new ObjectId(module.getId())));
        return mongoTemplate.find(query, LessonModel.class);
    }
}
