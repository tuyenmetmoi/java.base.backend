package com.tuyendv.web.backend.api.domain.system.resource.service;

import com.tuyendv.web.backend.api.dto.system.resource.ResourceRequestDTO;
import com.tuyendv.web.backend.api.dto.system.resource.ResourceResponseDTO;
import com.tuyendv.web.backend.api.dto.system.resource.ResourceSearchDTO;
import org.springframework.data.domain.Page;

public interface ResourceService {

    Page<ResourceResponseDTO> findResource(ResourceSearchDTO searchDTO);

    ResourceResponseDTO detailResource(Long id);

    ResourceResponseDTO createResource(ResourceRequestDTO dto);

    ResourceResponseDTO updateResource(ResourceRequestDTO dto);

    ResourceResponseDTO deleteResource(Long id);

}
