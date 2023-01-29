package com.ead.course.controllers;

import com.ead.course.dto.SubscriptionDto;
import com.ead.course.models.UserModel;
import com.ead.course.services.UserCourseService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.controller.path}"+"/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
public class UserCourseController {

    private final UserCourseService userCourseService;

    public UserCourseController(UserCourseService userCourseService) {
        this.userCourseService = userCourseService;
    }

    @PostMapping("/{courseId}/users/subscription")
    public ResponseEntity<SubscriptionDto> subscribeUserInCourse(@PathVariable String courseId,
                                                  @RequestBody @Valid SubscriptionDto subscriptionDto) {

        return ResponseEntity.ok(userCourseService.subscribeUserInCourse(courseId, subscriptionDto));
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUserFromCourse(@PathVariable(name="userId") String userId) {
        userCourseService.deleteUserFromCourse(userId);
    }

    @GetMapping("/{courseId}/users")
    public ResponseEntity<Page<UserModel>> getAllUsersByCourse(@PathVariable String courseId, Pageable pageable) {
        return ResponseEntity.ok(userCourseService.getAllUsersByCourse(courseId, pageable));
    }

}