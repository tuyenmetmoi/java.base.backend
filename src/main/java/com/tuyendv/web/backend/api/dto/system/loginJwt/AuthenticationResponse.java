package com.tuyendv.web.backend.api.dto.system.loginJwt;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AuthenticationResponse {

    private String token;

    private Date expiryTime;

}
