package com.tuyendv.web.backend.api.dto.common.pageable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageableDTO {

    private int page;

    private int size;

    private String sort;

}
