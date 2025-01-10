package com.tuyendv.web.backend.api.dto.system.resourceRole;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResourceRoleRequestDTO {

    String roleId;

    List<String> listInsert;

    List<String> listDelete;

}
