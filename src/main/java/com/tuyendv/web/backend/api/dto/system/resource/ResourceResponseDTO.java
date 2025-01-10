package com.tuyendv.web.backend.api.dto.system.resource;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ResourceResponseDTO {

    private Long rowNumber;

    private Long id;

    private String name;

    private String url;

    private String type;

    private String httpMethod;

    private Date regDate;

}
