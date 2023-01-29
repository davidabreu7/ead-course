package com.ead.course.models;

import com.ead.course.dto.UserEventDto;
import com.ead.course.enums.ActionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @Id
    private String id;
    private String username;
    private String fullname;
    private String email;
    private String userStatus;
    private String userType;
    private String cpf;
    private String imageUrl;
    private String phoneNumber;
    private ActionType actionType;

//    @DocumentReference(collection = "courses", lookup = "{ 'users': ?#{#self._id} }", lazy = true)
    private List<String> courses = new ArrayList<>();

    public UserModel(UserEventDto userDto) {
        this.id = userDto.getId();
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.fullname = userDto.getFullname();
        this.userStatus = userDto.getUserStatus();
        this.userType = userDto.getUserType();
        this.cpf = userDto.getCpf();
        this.imageUrl = userDto.getImageUrl();
        this.phoneNumber = userDto.getPhoneNumber();
        this.actionType = ActionType.valueOf(userDto.getActionType());
    }
}
