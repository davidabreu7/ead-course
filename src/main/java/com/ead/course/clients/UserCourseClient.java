package com.ead.course.clients;

import com.ead.course.dto.ResponsePageDto;
import com.ead.course.dto.UserRecord;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/users")
public interface UserCourseClient {

    @GetExchange()
    ResponsePageDto<UserRecord> getAllCoursesByStudent(@RequestParam(name = "courses") String id);
}
