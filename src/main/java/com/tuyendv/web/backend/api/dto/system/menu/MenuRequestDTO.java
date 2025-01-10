package com.tuyendv.web.backend.api.dto.system.menu;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuRequestDTO {

    private Long id;

    private String siteType;

    private String name;

    private String route;

    private Long parentId;

    private Integer displayOrder;

    private String vnName;

    private String screenName;

}
