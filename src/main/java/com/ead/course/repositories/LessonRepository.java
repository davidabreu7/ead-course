package com.ead.course.repositories;

import com.ead.course.models.LessonModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends MongoRepository<LessonModel, String> {

}

