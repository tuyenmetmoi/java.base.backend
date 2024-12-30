package com.tuyendv.web.backend.api.dto.system.file;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class FileMngDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String fimId;
    private String fimFileCategory;
    private String fimFileName;
    private String fimFilePath;
    private String fimFileExt;
//    private long fimFileSize;
//    private String fimUseYn;
    private String fimReferKeyId;
//    private String fimFileOrgName;
//    private String fimSectionName;

}
