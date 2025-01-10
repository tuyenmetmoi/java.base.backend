package com.tuyendv.web.backend.api.domain.system.role.service;

import com.tuyendv.web.backend.api.dto.common.pageable.PageableDTO;
import com.tuyendv.web.backend.api.dto.system.role.RoleResponseDTO;
import org.springframework.data.domain.Page;

public interface RoleService {

    Page<RoleResponseDTO> getAllRole(PageableDTO pageDto);

}
