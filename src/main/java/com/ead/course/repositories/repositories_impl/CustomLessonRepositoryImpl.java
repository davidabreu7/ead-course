package com.ead.course.repositories.repositories_impl;

import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CustomLessonRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

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

    @Override
    public Page<LessonModel> findAllLessonsIntoModule(ModuleModel module, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("module").is(new ObjectId(module.getId())));
        query.with(pageable);
        List<LessonModel> lessonModels = mongoTemplate.find(query, LessonModel.class);

        return PageableExecutionUtils.getPage(lessonModels, pageable, () -> mongoTemplate.count(query, LessonModel.class));
    }
}

