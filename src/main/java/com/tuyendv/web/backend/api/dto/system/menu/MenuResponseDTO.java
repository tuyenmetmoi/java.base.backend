package com.tuyendv.web.backend.api.dto.system.menu;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuResponseDTO {

    private String rowNumber;

    private String id;

    private String siteType;

    private String name;

    private String route;

    private String parentId;

    private String displayOrder;

    private String deleteFlag;

    private String vnName;

    private String regId;

    private String regDate;

    private String screenName;

}
