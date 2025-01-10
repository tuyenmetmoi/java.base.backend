package com.tuyendv.web.backend.api.entity.system.role;

import com.tuyendv.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table (name = "role")
public class RoleEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

}
