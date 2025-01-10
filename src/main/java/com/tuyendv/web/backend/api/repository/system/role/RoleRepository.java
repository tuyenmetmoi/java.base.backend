package com.tuyendv.web.backend.api.repository.system.role;

import com.tuyendv.web.backend.api.entity.system.role.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

}
