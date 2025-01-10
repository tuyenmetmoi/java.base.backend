package com.tuyendv.web.backend.api.domain.system.resourceRole.service;

import com.tuyendv.web.backend.api.dto.system.resourceRole.ResourceRoleRequestDTO;
import com.tuyendv.web.backend.api.dto.system.resourceRole.ResourceRoleResponseDTO;

import java.util.List;

public interface ResourceRoleService {

    List<ResourceRoleResponseDTO> findResourceRoleByRoleId(Long roleId);

    List<ResourceRoleResponseDTO> createResourceRole(ResourceRoleRequestDTO dto);

}
