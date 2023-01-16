package com.ead.course.clients;

import com.ead.course.dto.UserRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient("authuser")
public interface UserCourseClient {

    @GetMapping("/users")
    Optional<Page<UserRecord>> getAllCoursesByStudent(@RequestParam(name = "courses") String id);

    @GetMapping("/users/{userId}")
    Optional<UserRecord> getUserById(@PathVariable String userId);

    @PostMapping("/users/{userId}/subscription")
    Optional<UserRecord> subscribeUserInCourse(@PathVariable String userId, @RequestParam String courseId);
}
