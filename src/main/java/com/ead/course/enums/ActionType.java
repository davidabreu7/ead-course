package com.ead.course.enums;

import com.ead.course.models.UserModel;
import com.ead.course.services.UserService;

public enum ActionType {
    CREATE,
    UPDATE,
    DELETE;

    public void performUserEvent (UserService userService, UserModel userModel) {
        switch (this) {
            case CREATE -> userService.createUser(userModel);
            case UPDATE -> userService.updateUser(userModel);
            case DELETE -> userService.deleteUser(userModel);
        }
    }
}
