package com.tuyendv.web.backend.api.dto.system.loginJwt;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequest {

    private String username;

    private String password;

}
