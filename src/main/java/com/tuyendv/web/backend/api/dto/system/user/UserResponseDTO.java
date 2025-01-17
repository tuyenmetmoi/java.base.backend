package com.tuyendv.web.backend.api.dto.system.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDTO {

    private Integer usId;

    private String userName;

    private String fullName;

    private String phone;

    private String email;

    private String gender;

}
