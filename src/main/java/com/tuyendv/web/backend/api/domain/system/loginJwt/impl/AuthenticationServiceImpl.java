package com.tuyendv.web.backend.api.domain.system.loginJwt.impl;

import com.tuyendv.web.backend.api.common.ApiStatus;
import com.tuyendv.web.backend.api.common.Constants;
import com.tuyendv.web.backend.api.config.security.handler.CustomException;
import com.tuyendv.web.backend.api.config.security.jwt.JwtTokenUtils;
import com.tuyendv.web.backend.api.domain.system.loginJwt.service.AuthenticationService;
import com.tuyendv.web.backend.api.dto.system.loginJwt.AuthenticationRequest;
import com.tuyendv.web.backend.api.dto.system.loginJwt.AuthenticationResponse;
import com.tuyendv.web.backend.api.entity.system.loginJwt.JwtUser;
import com.tuyendv.web.backend.api.repository.system.loginJwt.JwtUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private JwtUserRepository userRepository;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        //get user info in db by request.getUsername()
        JwtUser userInfo = userRepository.findByUserNameAndUseYn(request.getUsername(), Constants.STATE_Y)
                .orElseThrow(() -> new CustomException(ApiStatus.USER_NOT_FOUND));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), userInfo.getPassword());

        if (! authenticated) {
            log.error("Invalid username or password");
            throw new CustomException(ApiStatus.INVALID_CREDENTIALS);
        }

        String token = jwtTokenUtils.generateToken(userInfo);

        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(token);
        response.setExpiryTime(new Date());

        return response;
    }

}
