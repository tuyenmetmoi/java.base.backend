package com.tuyendv.web.backend.api.domain.system.menu.service;

import com.tuyendv.web.backend.api.dto.system.menu.MenuRequestDTO;
import com.tuyendv.web.backend.api.dto.system.menu.MenuResponseDTO;
import com.tuyendv.web.backend.api.dto.system.menu.MenuSearchRequestDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MenuService {

    Page<MenuResponseDTO> searchMenu(MenuSearchRequestDTO searchDTO);

    List<MenuResponseDTO> getListParent(String siteType);

    boolean createMenu(MenuRequestDTO dto);

    boolean updateMenu(MenuRequestDTO dto);

    MenuResponseDTO detailMenu(Long id);

    MenuResponseDTO updateDeleteFlagMenu(Long id);

}
