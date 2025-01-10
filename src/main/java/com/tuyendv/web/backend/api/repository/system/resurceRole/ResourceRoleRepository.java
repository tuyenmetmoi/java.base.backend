package com.tuyendv.web.backend.api.repository.system.resurceRole;

import com.tuyendv.web.backend.api.entity.system.resourceRole.IResourceRoleResponse;
import com.tuyendv.web.backend.api.entity.system.resourceRole.ResourceRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRoleRepository extends JpaRepository<ResourceRoleEntity, Long> {

    @Query (value = """
                SELECT srr.ID, srr.ROLE_ID, srr.RESOURCE_ID 
                FROM SYS_RESOURCE_ROLE srr 
                WHERE (:roleId IS NULL OR srr.ROLE_ID = :roleId)
                    AND (:resourceId IS NULL OR srr.RESOURCE_ID = :resourceId)
            """, nativeQuery = true)
    List<IResourceRoleResponse> findByRoleIdAndResourceId(@Param ("roleId") Long roleId,
            @Param ("resourceId") Long resourceId);

}
