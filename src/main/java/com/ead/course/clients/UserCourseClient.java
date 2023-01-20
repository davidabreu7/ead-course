package com.ead.course.clients;

import com.ead.course.dto.UserRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient("authuser-service")
public interface UserCourseClient {

    @GetMapping("/authuser-service/users")
    Optional<Page<UserRecord>> getAllCoursesByStudent(@RequestParam(name = "courses") String id);

    @GetMapping("/authuser-service/users/{userId}")
    Optional<UserRecord> getUserById(@PathVariable String userId);

    @PostMapping("/authuser-service/users/{userId}/subscription")
    Optional<UserRecord> subscribeUserInCourse(@PathVariable String userId, @RequestParam String courseId);

    @DeleteMapping("/authuser-service/users/courses/{courseId}")
    void deleteCourseFromUser(@PathVariable String courseId);
}
