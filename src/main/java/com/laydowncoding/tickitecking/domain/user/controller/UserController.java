package com.laydowncoding.tickitecking.domain.user.controller;

import com.laydowncoding.tickitecking.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
}
