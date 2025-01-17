package com.tuyendv.web.backend.api.domain.system.menu.impl;

import com.tuyendv.web.backend.api.common.ApiStatus;
import com.tuyendv.web.backend.api.common.Constants;
import com.tuyendv.web.backend.api.config.security.handler.CustomException;
import com.tuyendv.web.backend.api.domain.common.service.CommonService;
import com.tuyendv.web.backend.api.domain.system.menu.service.MenuService;
import com.tuyendv.web.backend.api.dto.system.menu.MenuRequestDTO;
import com.tuyendv.web.backend.api.dto.system.menu.MenuResponseDTO;
import com.tuyendv.web.backend.api.dto.system.menu.MenuSearchRequestDTO;
import com.tuyendv.web.backend.api.entity.system.menu.IMenuResponse;
import com.tuyendv.web.backend.api.entity.system.menu.MenuEntity;
import com.tuyendv.web.backend.api.repository.system.menu.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Page<MenuResponseDTO> searchMenu(MenuSearchRequestDTO searchDTO) {
        int page = searchDTO.getPage();
        int size = searchDTO.getSize();
        String sort = searchDTO.getSort();
        Pageable pageable = commonService.buildPageable(page, size, sort);

        searchDTO.setDelYn(Constants.STATE_N);
        Page<IMenuResponse> lstData = menuRepository.searchMenu(searchDTO, pageable);

        return lstData.map(item -> modelMapper.map(item, MenuResponseDTO.class));
    }

    @Override
    public List<MenuResponseDTO> getListParent(String siteType) {
        List<IMenuResponse> lstData = menuRepository.getListParent(siteType, Constants.STATE_N);

        return lstData.stream().map(item -> modelMapper.map(item, MenuResponseDTO.class)).toList();
    }

    @Override
    @Transactional
    public boolean createMenu(MenuRequestDTO dto) {
        boolean isExistRoute = true;

        int checkExistRoute = menuRepository.checkExistRouteInMenu(dto.getRoute(), Constants.STATE_N, dto.getId());
        if (checkExistRoute > 0) {
            isExistRoute = false;
        } else {
            MenuEntity entity = modelMapper.map(dto, MenuEntity.class);
            entity.setDelYn(Constants.STATE_N);
            menuRepository.save(entity);
        }

        return isExistRoute;
    }

    @Override
    public boolean updateMenu(MenuRequestDTO dto) {
        boolean isExistRoute = true;

        if (dto.getId() != null) {
            int checkExistRoute = menuRepository.checkExistRouteInMenu(dto.getRoute(), Constants.STATE_N, dto.getId());

            if (checkExistRoute > 0) {
                isExistRoute = false;
            } else {
                MenuEntity entity = menuRepository.findById(dto.getId())
                        .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
                modelMapper.map(dto, entity);
                menuRepository.save(entity);
            }
        } else {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        return isExistRoute;
    }

    @Override
    public MenuResponseDTO detailMenu(Integer id) {
        MenuEntity entity = menuRepository.findById(id).orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));

        return modelMapper.map(entity, MenuResponseDTO.class);
    }

    @Override
    public MenuResponseDTO updateDeleteFlagMenu(Integer id) {
        MenuEntity deleteMenu = menuRepository.findById(id).orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));

        List<MenuEntity> lstChildren = menuRepository.findAllByParentId(deleteMenu.getMnId());
        lstChildren.forEach(item -> {
            item.setDelYn(Constants.STATE_Y);
        });
        menuRepository.saveAll(lstChildren);

        deleteMenu.setDelYn(Constants.STATE_N);
        menuRepository.save(deleteMenu);

        return modelMapper.map(deleteMenu, MenuResponseDTO.class);
    }

}
