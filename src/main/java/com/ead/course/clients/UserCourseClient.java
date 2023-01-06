package com.ead.course.clients;

import com.ead.course.dto.ResponsePageDto;
import com.ead.course.dto.UserRecord;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Optional;

@HttpExchange("/users")
public interface UserCourseClient {

    @GetExchange()
    Optional<ResponsePageDto<UserRecord>> getAllCoursesByStudent(@RequestParam(name = "courses") String id);

    @GetExchange("/{userId}")
    Optional<UserRecord> getUserById(@PathVariable String userId);

    @PostExchange("/{userId}/subscription")
    Optional<UserRecord> subscribeUserInCourse(@PathVariable String userId, @RequestParam String courseId);
}
