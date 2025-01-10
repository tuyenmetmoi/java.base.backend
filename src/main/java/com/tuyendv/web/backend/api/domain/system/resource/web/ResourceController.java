package com.tuyendv.web.backend.api.domain.system.resource.web;

import com.tuyendv.web.backend.api.common.ApiResponse;
import com.tuyendv.web.backend.api.common.ApiStatus;
import com.tuyendv.web.backend.api.domain.system.resource.service.ResourceService;
import com.tuyendv.web.backend.api.dto.system.resource.ResourceRequestDTO;
import com.tuyendv.web.backend.api.dto.system.resource.ResourceResponseDTO;
import com.tuyendv.web.backend.api.dto.system.resource.ResourceSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/a/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping ("/list")
    public ApiResponse<Page<ResourceResponseDTO>> searchResource(@RequestBody ResourceSearchDTO searchDTO) {
        Page<ResourceResponseDTO> searchResults = resourceService.findResource(searchDTO);

        return new ApiResponse<>(ApiStatus.SUCCESS, searchResults);
    }

    @PostMapping ("/detail")
    public ApiResponse<ResourceResponseDTO> getResourceById(@RequestBody String id) {
        ResourceResponseDTO detailResource = resourceService.detailResource(Long.parseLong(id));

        return new ApiResponse<>(ApiStatus.SUCCESS, detailResource);
    }

    @PostMapping ("/create")
    public ApiResponse<ResourceResponseDTO> createMenu(@RequestBody ResourceRequestDTO dto) {
        ResourceResponseDTO createResults = resourceService.createResource(dto);

        return new ApiResponse<>(ApiStatus.CREATED, createResults);
    }

    @PutMapping ("/update")
    public ApiResponse<ResourceResponseDTO> updateMenu(@RequestBody ResourceRequestDTO dto) {
        ResourceResponseDTO updateResult = resourceService.updateResource(dto);

        return new ApiResponse<>(ApiStatus.SUCCESS, updateResult);
    }

    @DeleteMapping ("/delete")
    public ApiResponse<ResourceResponseDTO> deleteResourceById(@RequestBody String id) {
        ResourceResponseDTO deleteResource = resourceService.deleteResource(Long.parseLong(id));

        return new ApiResponse<>(ApiStatus.SUCCESS, deleteResource);
    }

}
