package com.ead.course.services;

import com.ead.course.clients.UserCourseClient;
import com.ead.course.dto.ResponsePageDto;
import com.ead.course.dto.UserRecord;
import org.springframework.stereotype.Service;

@Service
public class UserCourseService {

    private final UserCourseClient userCourseClient;

    public UserCourseService(UserCourseClient userCourseClient) {
        this.userCourseClient = userCourseClient;
    }

    public ResponsePageDto<UserRecord> getAllCoursesByStudent(String id) {
        return userCourseClient.getAllCoursesByStudent(id);
    }
}
