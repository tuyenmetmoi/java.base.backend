package com.tuyendv.web.backend.api.dto.system.resource;

import com.tuyendv.web.backend.api.dto.common.pageable.PageableDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceSearchDTO extends PageableDTO {

    private String url;

    private String name;

    private String method;

    private String type;

    private String startDate;

    private String endDate;

}
