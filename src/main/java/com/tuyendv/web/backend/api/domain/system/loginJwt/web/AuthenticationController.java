package com.tuyendv.web.backend.api.domain.system.loginJwt.web;

import com.tuyendv.web.backend.api.common.ApiResponse;
import com.tuyendv.web.backend.api.common.ApiStatus;
import com.tuyendv.web.backend.api.domain.system.loginJwt.service.AuthenticationService;
import com.tuyendv.web.backend.api.dto.system.loginJwt.AuthenticationRequest;
import com.tuyendv.web.backend.api.dto.system.loginJwt.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse result = authenticationService.login(request);

        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

}
