package com.tuyendv.web.backend.api.domain.system.role.web;

import com.tuyendv.web.backend.api.common.ApiResponse;
import com.tuyendv.web.backend.api.common.ApiStatus;
import com.tuyendv.web.backend.api.domain.system.role.service.RoleService;
import com.tuyendv.web.backend.api.dto.common.pageable.PageableDTO;
import com.tuyendv.web.backend.api.dto.system.role.RoleResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/a/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping ("/list")
    public ApiResponse<Page<RoleResponseDTO>> getAllRole(@RequestBody PageableDTO pageDto) {
        Page<RoleResponseDTO> lstResults = roleService.getAllRole(pageDto);

        return new ApiResponse<>(ApiStatus.SUCCESS, lstResults);
    }

}
