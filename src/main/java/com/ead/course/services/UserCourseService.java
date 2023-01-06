package com.ead.course.services;

import com.ead.course.clients.UserCourseClient;
import com.ead.course.dto.ResponsePageDto;
import com.ead.course.dto.SubscriptionDto;
import com.ead.course.dto.UserRecord;
import com.ead.course.exceptions.ResourceNotFoundException;
import com.ead.course.exceptions.SubscriptionException;
import com.ead.course.models.CourseModel;
import com.ead.course.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserCourseService {

    private final UserCourseClient userCourseClient;
    final CourseRepository courseRepository;

    private static final String USER_NOT_FOUND = "User not found";

    public UserCourseService(UserCourseClient userCourseClient, CourseRepository courseRepository) {
        this.userCourseClient = userCourseClient;
        this.courseRepository = courseRepository;
    }

    public ResponsePageDto<UserRecord> getAllCoursesByStudent(String id) {
        return userCourseClient.getAllCoursesByStudent(id).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
    }

    public UserRecord getUserById(String id) {
        return userCourseClient.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
    }

    public UserRecord subscribeCourseInUser(String userId, String courseId) {
        return userCourseClient.subscribeUserInCourse(userId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
    }

    @Transactional
    public SubscriptionDto subscribeUserInCourse(String courseId, SubscriptionDto subscriptionDto) {
        CourseModel course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        if (course.getUsers().contains(subscriptionDto.getUserId())) {
            throw new SubscriptionException("User already subscribed in this course");
        }

        UserRecord user = getUserById(subscriptionDto.getUserId());
        if (user.userStatus().equals("BLOCKED")) {
            throw new SubscriptionException("User is inactive");
        }

        UserRecord userSub = subscribeCourseInUser(user.id(), course.getId());
        course.getUsers().add(user.id());
        courseRepository.save(course);

        return buildSubscription(subscriptionDto, course, userSub);
    }

    private static SubscriptionDto buildSubscription(SubscriptionDto subscriptionDto, CourseModel course, UserRecord user) {
        subscriptionDto.setCourseId(course.getId());
        subscriptionDto.setCourseName(course.getName());
        subscriptionDto.setUserId(user.id());
        subscriptionDto.setUserName(user.username());
        subscriptionDto.setMessage("User subscribed");
        return subscriptionDto;
    }
}
