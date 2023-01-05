package com.ead.course.repositories;

import com.ead.course.models.CourseModel;
import com.ead.course.models.QCourseModel;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends MongoRepository<CourseModel, String> ,
        QuerydslPredicateExecutor<CourseModel>,
        QuerydslBinderCustomizer<QCourseModel> {


    Optional<CourseModel> findById(String id);

    Page<CourseModel> findAll(Pageable pageable);

    @Override
    default void customize(QuerydslBindings bindings, QCourseModel root) {
        bindings.bind(root.name, root.description).all((path, value) -> {
            BooleanBuilder builder = new BooleanBuilder();
            value.forEach(v -> builder.or(path.containsIgnoreCase(v)));
            return Optional.of(builder);
        });
      bindings.bind(root.users).first((path, value) -> {
            BooleanBuilder builder = new BooleanBuilder();
            value.forEach(user -> builder.or(root.users.contains(user)));
            return builder;
        });
    }

}




