package com.tuyendv.web.backend.api.dto.system.menu;

import com.tuyendv.web.backend.api.dto.common.pageable.PageableDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuSearchRequestDTO extends PageableDTO {

    private String vnName;

    private String name;

    private String route;

    private String delYn;

}
