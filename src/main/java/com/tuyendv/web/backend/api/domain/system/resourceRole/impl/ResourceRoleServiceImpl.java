package com.tuyendv.web.backend.api.domain.system.resourceRole.impl;

import com.tuyendv.web.backend.api.common.ApiStatus;
import com.tuyendv.web.backend.api.config.security.handler.CustomException;
import com.tuyendv.web.backend.api.domain.system.resourceRole.service.ResourceRoleService;
import com.tuyendv.web.backend.api.dto.system.resourceRole.ResourceRoleRequestDTO;
import com.tuyendv.web.backend.api.dto.system.resourceRole.ResourceRoleResponseDTO;
import com.tuyendv.web.backend.api.entity.system.resource.ResourceEntity;
import com.tuyendv.web.backend.api.entity.system.resourceRole.IResourceRoleResponse;
import com.tuyendv.web.backend.api.entity.system.resourceRole.ResourceRoleEntity;
import com.tuyendv.web.backend.api.entity.system.role.RoleEntity;
import com.tuyendv.web.backend.api.repository.system.resource.ResourceRepository;
import com.tuyendv.web.backend.api.repository.system.resurceRole.ResourceRoleRepository;
import com.tuyendv.web.backend.api.repository.system.role.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceRoleServiceImpl implements ResourceRoleService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceRoleRepository resourceRoleRepository;

    @Override
    public List<ResourceRoleResponseDTO> findResourceRoleByRoleId(Long roleId) {
        List<IResourceRoleResponse> lstData = resourceRoleRepository.findByRoleIdAndResourceId(roleId, null);
        return lstData.stream().map(item -> modelMapper.map(item, ResourceRoleResponseDTO.class)).toList();
    }

    @Override
    public List<ResourceRoleResponseDTO> createResourceRole(ResourceRoleRequestDTO dto) {
        List<ResourceRoleResponseDTO> lstResults = new ArrayList<>();

        if (dto != null) {
            RoleEntity roleEntity = roleRepository.findById(Integer.valueOf(dto.getRoleId()))
                    .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));

            if (! dto.getListInsert().isEmpty()) {
                for (String resourceId : dto.getListInsert()) {
                    ResourceEntity resourceEntity = resourceRepository.findById(Integer.valueOf(resourceId))
                            .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));

                    ResourceRoleEntity entity = new ResourceRoleEntity();
                    entity.setRlId(roleEntity.getRlId());
                    entity.setRsId(resourceEntity.getRsId());

                    resourceRoleRepository.save(entity);
                    lstResults.add(modelMapper.map(entity, ResourceRoleResponseDTO.class));
                }
            }

            if (! dto.getListDelete().isEmpty()) {
                for (String resourceId : dto.getListDelete()) {
                    List<ResourceRoleEntity> lstInDb = resourceRoleRepository.findByRoleIdAndResourceId(
                                    Long.parseLong(dto.getRoleId()), Long.parseLong(resourceId)).stream()
                            .map(item -> modelMapper.map(item, ResourceRoleEntity.class)).toList();

                    if (! lstInDb.isEmpty()) {
                        resourceRoleRepository.delete(lstInDb.getFirst());
                    }
                }
            }
        }

        return lstResults;
    }

}
