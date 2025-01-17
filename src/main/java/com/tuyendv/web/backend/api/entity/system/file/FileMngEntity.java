package com.tuyendv.web.backend.api.entity.system.file;

import com.tuyendv.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@Setter
@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "FILE_MNG")
public class FileMngEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String fimId;

    private String fimFileCategory;

    private String fimFileName;

    private String fimFilePath;

    private String fimFileExt;

    private String fimReferKeyId;

    private String delYn;

}
