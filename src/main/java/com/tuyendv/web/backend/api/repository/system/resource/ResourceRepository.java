package com.tuyendv.web.backend.api.repository.system.resource;

import com.tuyendv.web.backend.api.dto.system.resource.ResourceSearchDTO;
import com.tuyendv.web.backend.api.entity.system.resource.IResourceSearchResponse;
import com.tuyendv.web.backend.api.entity.system.resource.ResourceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Integer> {

    @Query (value = """
                SELECT ROW_NUMBER() OVER (ORDER BY sr.id DESC) AS rowNumber, 
                    sr.ID,
                    sr.URL,
                    sr.HTTP_METHOD,
                    sr.NAME,
                    sr.TYPE,
                    sr.REG_DATE
                FROM sys_resource sr
                WHERE (:#{#searchRequest.name} IS NULL OR LOWER(sr.NAME) LIKE ('%' || LOWER(:#{#searchRequest.name}) || '%'))
                    AND (:#{#searchRequest.url} IS NULL OR LOWER(sr.URL) LIKE ('%' || LOWER(:#{#searchRequest.url}) || '%'))
                    AND (:#{#searchRequest.type} IS NULL OR sr.TYPE = %:#{#searchRequest.type}%)
                    AND (:#{#searchRequest.method} IS NULL OR sr.HTTP_METHOD = %:#{#searchRequest.method}%)
                    AND (:#{#searchRequest.startDate} IS NULL OR TO_CHAR(REG_DATE, 'yyyy-MM-dd') >=  :#{#searchRequest.startDate}) 
                    AND (:#{#searchRequest.endDate} IS NULL OR TO_CHAR(REG_DATE, 'yyyy-MM-dd') <=  :#{#searchRequest.endDate})
            """, nativeQuery = true)
    Page<IResourceSearchResponse> searchResource(@Param ("searchRequest") ResourceSearchDTO searchRequest,
            Pageable pageable);

}
