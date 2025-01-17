package com.tuyendv.web.backend.api.domain.system.user.service;

import com.tuyendv.web.backend.api.dto.system.user.UserRequestDTO;
import com.tuyendv.web.backend.api.dto.system.user.UserResponseDTO;

public interface UserService {

    UserResponseDTO registerUser(UserRequestDTO dto);

}
