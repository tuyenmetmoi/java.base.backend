package com.tuyendv.web.backend.api.entity.common;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

public class CustomAuditingEntityListener {

    private static final AtomicLong counter = new AtomicLong();

    @PrePersist
    public void setCreatedDate(AbstractAuditingEntity<?> entity) {
        long uniqueTime = Instant.now().toEpochMilli() + counter.incrementAndGet();
        entity.setRegDate(Instant.ofEpochMilli(uniqueTime));
        entity.setModifyDate(Instant.ofEpochMilli(uniqueTime));
    }

    @PreUpdate
    public void setModifiedDate(AbstractAuditingEntity<?> entity) {
        long uniqueTime = Instant.now().toEpochMilli() + counter.incrementAndGet();
        entity.setModifyDate(Instant.ofEpochMilli(uniqueTime));
    }

}
