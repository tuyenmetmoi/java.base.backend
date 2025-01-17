package com.tuyendv.web.backend.api.domain.system.menu.web;

import com.tuyendv.web.backend.api.common.ApiResponse;
import com.tuyendv.web.backend.api.common.ApiStatus;
import com.tuyendv.web.backend.api.domain.system.menu.service.MenuService;
import com.tuyendv.web.backend.api.dto.system.menu.MenuRequestDTO;
import com.tuyendv.web.backend.api.dto.system.menu.MenuResponseDTO;
import com.tuyendv.web.backend.api.dto.system.menu.MenuSearchRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/a/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping ("/list")
    public ApiResponse<Page<MenuResponseDTO>> searchMenu(@RequestBody MenuSearchRequestDTO searchDTO) {
        Page<MenuResponseDTO> searchResults = menuService.searchMenu(searchDTO);

        return new ApiResponse<>(ApiStatus.SUCCESS, searchResults);
    }

    @PostMapping ("/list-parent")
    public ApiResponse<List<MenuResponseDTO>> getListParent(@RequestBody String siteType) {
        List<MenuResponseDTO> searchResults = menuService.getListParent(siteType);

        return new ApiResponse<>(ApiStatus.SUCCESS, searchResults);
    }

    @PostMapping ("/create")
    public ApiResponse<Boolean> createMenu(@RequestBody MenuRequestDTO dto) {
        boolean result = menuService.createMenu(dto);

        return new ApiResponse<>(ApiStatus.CREATED, result);
    }

    @PutMapping ("/update")
    public ApiResponse<Boolean> updateMenu(@RequestBody MenuRequestDTO dto) {
        boolean result = menuService.updateMenu(dto);

        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/detail")
    public ApiResponse<MenuResponseDTO> getMenuById(@RequestBody String id) {
        MenuResponseDTO detailMenu = menuService.detailMenu(Integer.valueOf(id));

        return new ApiResponse<>(ApiStatus.SUCCESS, detailMenu);
    }

    @PutMapping ("/delete")
    public ApiResponse<MenuResponseDTO> updateDeleteFlag(@RequestBody String id) {
        MenuResponseDTO deleteResult = menuService.updateDeleteFlagMenu(Integer.valueOf(id));

        return new ApiResponse<>(ApiStatus.SUCCESS, deleteResult);
    }

}
