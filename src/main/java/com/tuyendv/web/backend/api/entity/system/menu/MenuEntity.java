package com.tuyendv.web.backend.api.entity.system.menu;

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
@Table (name = "menu")
public class MenuEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String siteType;

    private String name;

    private String route;

    private Long parentId;

    private Integer displayOrder;

    private String delYn;

    private String vnName;

    private String screenName; //vue use it search url

}
