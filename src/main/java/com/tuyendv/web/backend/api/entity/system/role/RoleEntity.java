package com.tuyendv.web.backend.api.entity.system.role;

import com.tuyendv.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "SYS_ROLE")
public class RoleEntity implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer rlId;

    private String roleCode;

    private String roleName;

    private String category;

    private String roleDesc;

}
