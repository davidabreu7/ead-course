package com.ead.course.controllers;

import com.ead.course.dto.ResponsePageDto;
import com.ead.course.dto.UserRecord;
import com.ead.course.services.UserCourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
public class UserCourseController {

    private final UserCourseService userCourseService;

    public UserCourseController(UserCourseService userCourseService) {
        this.userCourseService = userCourseService;
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<ResponsePageDto<UserRecord>> getAllCoursesByStudent(@PathVariable String id) {
        return ResponseEntity.ok(userCourseService.getAllCoursesByStudent(id));
    }
}