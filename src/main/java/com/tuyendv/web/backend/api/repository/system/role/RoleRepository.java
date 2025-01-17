package com.tuyendv.web.backend.api.repository.system.role;

import com.tuyendv.web.backend.api.entity.system.role.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    @Query (value = """
                SELECT
                    sr.role_code,
                    sr.role_name,
                    sr.category,
                    sr.role_desc
                FROM sys_role sr
                    JOIN sys_user_role sur ON sur.rl_id = sr.rl_id
                JOIN sys_user su ON su.us_id = sur.us_id
            """, nativeQuery = true)
    List<RoleEntity> findAllByUserIdAndUseYnAndDelYn(String userId, String useYn, String delYn);

}
