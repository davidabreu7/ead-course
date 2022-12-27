package com.ead.course.repositories;

import com.ead.course.models.CourseModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends MongoRepository<CourseModel, String> {


    Optional<CourseModel> findById(String id);
}

