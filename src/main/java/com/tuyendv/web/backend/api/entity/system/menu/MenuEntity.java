package com.tuyendv.web.backend.api.entity.system.menu;

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
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@Setter
@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "SYS_MENU")
public class MenuEntity extends AbstractAuditingEntity<Integer> implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer mnId;

    private String siteType;

    private String name;

    private String route;

    private Integer parentId;

    private Integer displayOrder;

    private String delYn;

    private String vnName;

    private String screenName; //vue use it search url

}
