package com.tuyendv.web.backend.api.domain.system.user.web;

import com.tuyendv.web.backend.api.common.ApiResponse;
import com.tuyendv.web.backend.api.common.ApiStatus;
import com.tuyendv.web.backend.api.domain.system.user.service.UserService;
import com.tuyendv.web.backend.api.dto.system.user.UserRequestDTO;
import com.tuyendv.web.backend.api.dto.system.user.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/c")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    private ApiResponse<UserResponseDTO> create(@RequestBody UserRequestDTO dto) {
        UserResponseDTO result = userService.registerUser(dto);

        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

}
