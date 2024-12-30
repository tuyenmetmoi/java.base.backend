package com.tuyendv.web.backend.api.domain.system.loginJwt.service;

import com.tuyendv.web.backend.api.dto.system.loginJwt.AuthenticationRequest;
import com.tuyendv.web.backend.api.dto.system.loginJwt.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

}
