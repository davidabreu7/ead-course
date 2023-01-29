package com.ead.course.repositories;

import com.ead.course.models.QUserModel;
import com.ead.course.models.UserModel;
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
public interface UserRepository extends MongoRepository<UserModel, String>,
        QuerydslPredicateExecutor<UserModel>,
        QuerydslBinderCustomizer<QUserModel>
         {

    default void customize(QuerydslBindings bindings, QUserModel root) {
        bindings.bind(root.username, root.email, root.fullname).all((path, value) -> {
            BooleanBuilder builder = new BooleanBuilder();
            value.forEach(v -> builder.or(path.containsIgnoreCase(v)));
            return Optional.of(builder);
        });
        bindings.bind(root.courses).first((path, value) -> {
            BooleanBuilder builder = new BooleanBuilder();
            value.forEach(course -> builder.or(root.courses.contains(course)));
            return builder;
        });
    }

    default Page<UserModel> findAllUsersByCourse(String courseId, Pageable pageable) {
        QUserModel user = QUserModel.userModel;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(user.courses.contains(courseId));
        return findAll(predicate, pageable);
    }

}
