package com.tuyendv.web.backend.api.entity.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@MappedSuperclass //mark it supper class
@EntityListeners ({AuditingEntityListener.class, CustomAuditingEntityListener.class})
@JsonIgnoreProperties (value = {"regId", "regDate", "modifyId", "modifyDate"}, allowGetters = true)
public abstract class AbstractAuditingEntity<T> implements Serializable {

    @CreatedBy
    @Column (name = "reg_id", length = 50, updatable = false)
    private String regId;

    @CreatedDate
    @Column (name = "reg_date", updatable = false)
    private Instant regDate;

    @LastModifiedBy
    @Column (name = "modify_Id", length = 50)
    private String modifyId;

    @LastModifiedDate
    @Column (name = "modify_date")
    private Instant modifyDate;

}
