package com.tuyendv.web.backend.api.domain.system.resource.impl;

import com.tuyendv.web.backend.api.common.ApiStatus;
import com.tuyendv.web.backend.api.config.security.handler.CustomException;
import com.tuyendv.web.backend.api.domain.common.service.CommonService;
import com.tuyendv.web.backend.api.domain.system.resource.service.ResourceService;
import com.tuyendv.web.backend.api.dto.system.resource.ResourceRequestDTO;
import com.tuyendv.web.backend.api.dto.system.resource.ResourceResponseDTO;
import com.tuyendv.web.backend.api.dto.system.resource.ResourceSearchDTO;
import com.tuyendv.web.backend.api.entity.system.resource.IResourceSearchResponse;
import com.tuyendv.web.backend.api.entity.system.resource.ResourceEntity;
import com.tuyendv.web.backend.api.repository.system.resource.ResourceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public Page<ResourceResponseDTO> findResource(ResourceSearchDTO searchDTO) {
        int page = searchDTO.getPage();
        int size = searchDTO.getSize();
        String sort = searchDTO.getSort();
        Pageable pageable = commonService.buildPageable(page, size, sort);

        Page<IResourceSearchResponse> lstResults = resourceRepository.searchResource(searchDTO, pageable);
        return lstResults.map(item -> modelMapper.map(item, ResourceResponseDTO.class));
    }

    @Override
    public ResourceResponseDTO detailResource(Integer id) {
        ResourceEntity entityExist = resourceRepository.findById(id)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));

        return modelMapper.map(entityExist, ResourceResponseDTO.class);
    }

    @Override
    public ResourceResponseDTO createResource(ResourceRequestDTO dto) {
        ResourceEntity entity = modelMapper.map(dto, ResourceEntity.class);

        return modelMapper.map(resourceRepository.save(entity), ResourceResponseDTO.class);
    }

    @Override
    public ResourceResponseDTO updateResource(ResourceRequestDTO dto) {
        if (dto.getId() != null) {
            ResourceEntity entity = resourceRepository.findById(dto.getId())
                    .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));

            modelMapper.map(dto, entity);

            return modelMapper.map(resourceRepository.save(entity), ResourceResponseDTO.class);
        } else {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }
    }

    @Override
    public ResourceResponseDTO deleteResource(Integer id) {
        ResourceEntity entity = resourceRepository.findById(id)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));

        resourceRepository.delete(entity);
        return modelMapper.map(entity, ResourceResponseDTO.class);
    }

}
