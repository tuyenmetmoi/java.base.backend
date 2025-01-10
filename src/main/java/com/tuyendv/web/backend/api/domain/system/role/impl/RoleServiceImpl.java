package com.tuyendv.web.backend.api.domain.system.role.impl;

import com.tuyendv.web.backend.api.domain.common.service.CommonService;
import com.tuyendv.web.backend.api.domain.system.role.service.RoleService;
import com.tuyendv.web.backend.api.dto.common.pageable.PageableDTO;
import com.tuyendv.web.backend.api.dto.system.role.RoleResponseDTO;
import com.tuyendv.web.backend.api.entity.system.role.RoleEntity;
import com.tuyendv.web.backend.api.repository.system.role.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<RoleResponseDTO> getAllRole(PageableDTO pageDto) {
        int page = pageDto.getPage();
        int size = pageDto.getSize();
        String sort = pageDto.getSort();
        Pageable pageable = commonService.buildPageable(page, size, sort);

        Page<RoleEntity> lstResults = roleRepository.findAll(pageable);
        return lstResults.map(item -> modelMapper.map(item, RoleResponseDTO.class));
    }

}
