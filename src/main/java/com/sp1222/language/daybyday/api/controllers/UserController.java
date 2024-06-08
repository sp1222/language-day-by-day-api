package com.sp1222.language.daybyday.api.controllers;

import com.sp1222.language.daybyday.api.dto.UserDto;
import com.sp1222.language.daybyday.api.exceptions.NotFoundByIdException;
import com.sp1222.language.daybyday.api.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    UserService userService;

    public ResponseEntity<UserDto> getUser(UserDto user) {
        // TODO: authenticate user
        return null;
    }

    public ResponseEntity<UserDto> createUser(UserDto newUser) throws NotFoundByIdException {
        return ResponseEntity.ok(userService.createUser(newUser));
    }
}
