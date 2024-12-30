package com.tuyendv.web.backend.api.dto.system.file;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class MultipleFileDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<String> listFimReferKeyId;

    private List<String> listFimFileCategory;

}
