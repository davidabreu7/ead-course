package com.ead.course.services;

import com.ead.course.models.UserModel;
import com.ead.course.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserService {

    final
    UserRepository userRepository;

    private final UserCourseService userCourseService;

    public UserService(UserRepository userRepository, UserCourseService userCourseService) {
        this.userRepository = userRepository;
        this.userCourseService = userCourseService;
    }

    public void createUser(UserModel userModel) {
        log.info("(MESSAGE createUser) User created: {}", userModel.toString());
        userRepository.save(userModel);
    }

    public void updateUser(UserModel userModel) {
        log.info("(MESSAGE updateUser) User updated: {}", userModel.toString());
        userRepository.save(userModel);
    }

    public void deleteUser(UserModel userModel) {
        log.info("(MESSAGE deleteUser) User deleted: {}", userModel.toString());
        userCourseService.deleteUserFromCourse(userModel.getId());
        userRepository.delete(userModel);
    }
}
