package com.ead.course.services;

import com.ead.course.dto.SubscriptionDto;
import com.ead.course.exceptions.ResourceNotFoundException;
import com.ead.course.exceptions.SubscriptionException;
import com.ead.course.models.CourseModel;
import com.ead.course.models.UserModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserCourseService {

    final CourseRepository courseRepository;

    final UserRepository userRepository;

    private static final String USER_NOT_FOUND = "User not found";

    public UserCourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public SubscriptionDto subscribeUserInCourse(String courseId, SubscriptionDto subscriptionDto) {
        CourseModel course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        if (course.getUsers().contains(subscriptionDto.getUserId())) {
            throw new SubscriptionException("User already subscribed in this course");
        }
        var user = userRepository.findById(subscriptionDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        if(user.getUserStatus().equals("BLOCKED")){
            throw new SubscriptionException("User is blocked");
        }

        course.getUsers().add(subscriptionDto.getUserId());
        courseRepository.save(course);
        user.getCourses().add(courseId);
        userRepository.save(user);

        return buildSubscription(subscriptionDto, course, user);
    }

    private static SubscriptionDto buildSubscription(SubscriptionDto subscriptionDto, CourseModel course, UserModel user) {
        subscriptionDto.setCourseId(course.getId());
        subscriptionDto.setCourseName(course.getName());
        subscriptionDto.setUserId(user.getId());
        subscriptionDto.setUserName(user.getUsername());
        subscriptionDto.setMessage("User subscribed");
        return subscriptionDto;
    }

    @Transactional
    public void deleteUserFromCourse(String userId) {
        List<CourseModel> courses = courseRepository.findAllCoursesByUser(userId);
        if (courses.isEmpty()) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }
        courses.forEach(course -> {
            course.getUsers().remove(userId);
            courseRepository.save(course);
        });
    }


    public Page<UserModel> getAllUsersByCourse(String courseId, Pageable pageable) {
        return userRepository.findAllUsersByCourse(courseId, pageable);
    }
}

