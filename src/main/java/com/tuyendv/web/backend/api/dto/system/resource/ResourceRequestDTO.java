package com.tuyendv.web.backend.api.dto.system.resource;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceRequestDTO {

    private Integer id;

    private String name;

    private String url;

    private String type;

    private String httpMethod;

}
