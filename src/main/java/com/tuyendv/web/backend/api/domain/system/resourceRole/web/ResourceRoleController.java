package com.tuyendv.web.backend.api.domain.system.resourceRole.web;

import com.tuyendv.web.backend.api.common.ApiResponse;
import com.tuyendv.web.backend.api.common.ApiStatus;
import com.tuyendv.web.backend.api.domain.system.resourceRole.service.ResourceRoleService;
import com.tuyendv.web.backend.api.dto.system.resourceRole.ResourceRoleRequestDTO;
import com.tuyendv.web.backend.api.dto.system.resourceRole.ResourceRoleResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/a/resource-role")
public class ResourceRoleController {

    @Autowired
    private ResourceRoleService resourceRoleService;

    @PostMapping ("/")
    public ApiResponse<List<ResourceRoleResponseDTO>> findResourceRoleByRoleId(@RequestBody String roleId) {
        List<ResourceRoleResponseDTO> lstResourceRole = resourceRoleService.findResourceRoleByRoleId(
                Long.parseLong(roleId));

        return new ApiResponse<>(ApiStatus.SUCCESS, lstResourceRole);
    }

    @PostMapping ("/create")
    public ApiResponse<List<ResourceRoleResponseDTO>> createResourceRole(@RequestBody ResourceRoleRequestDTO dto) {
        List<ResourceRoleResponseDTO> lstResourceRole = resourceRoleService.createResourceRole(dto);

        return new ApiResponse<>(ApiStatus.CREATED, lstResourceRole);
    }

}
