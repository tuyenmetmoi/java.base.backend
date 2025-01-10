package com.tuyendv.web.backend.api.entity.common;

import com.tuyendv.web.backend.api.util.system.WebUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

public class CustomAuditingEntityListener {

    private static final AtomicLong counter = new AtomicLong();

    @PrePersist
    public void setCreatedDate(AbstractAuditingEntity<?> entity) {
        long uniqueTime = Instant.now().toEpochMilli() + counter.incrementAndGet();
        entity.setRegDate(Instant.ofEpochMilli(uniqueTime));
        entity.setModifyDate(Instant.ofEpochMilli(uniqueTime));
        entity.setRegIp(getIp());
    }

    @PreUpdate
    public void setModifiedDate(AbstractAuditingEntity<?> entity) {
        long uniqueTime = Instant.now().toEpochMilli() + counter.incrementAndGet();
        entity.setModifyDate(Instant.ofEpochMilli(uniqueTime));
        entity.setModifyIp(getIp());
    }

    public String getIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return WebUtil.getClientIpAddress(request);
    }

}
