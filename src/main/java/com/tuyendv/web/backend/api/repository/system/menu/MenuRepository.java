package com.tuyendv.web.backend.api.repository.system.menu;

import com.tuyendv.web.backend.api.dto.system.menu.MenuSearchRequestDTO;
import com.tuyendv.web.backend.api.entity.system.menu.IMenuResponse;
import com.tuyendv.web.backend.api.entity.system.menu.MenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {

    @Query (value = """
                SELECT ROW_NUMBER() OVER (ORDER BY mn.REG_DATE) AS rowNumber, 
                    mn.ID,
                    mn.SITE_TYPE,
                    mn.NAME,
                    mn.ROUTE,
                    mn.PARENT_ID,
                    mn.DISPLAY_ORDER,
                    mn.VN_NAME,
                    mn.REG_ID,
                    mn.REG_DATE
                FROM MENU mn
                WHERE mn.DEL_YN = :#{#dto.delYn}
                    AND (:#{#dto.vnName} IS NULL OR LOWER(mn.VN_NAME) LIKE ('%' || LOWER(:#{#dto.vnName}) || '%'))
                    AND (:#{#dto.name} IS NULL OR LOWER(mn.NAME) LIKE ('%' || LOWER(:#{#dto.name}) || '%'))
                    AND (:#{#dto.route} IS NULL OR LOWER(mn.ROUTE) LIKE ('%' || LOWER(:#{#dto.route}) || '%'))
                ORDER BY mn.REG_DATE DESC
            """, nativeQuery = true)
    Page<IMenuResponse> searchMenu(@Param ("dto") MenuSearchRequestDTO dto, Pageable pageable);

    @Query (value = """
                SELECT ROW_NUMBER() OVER (ORDER BY mn.REG_DATE) AS rowNumber,
                    mn.ID,
                    mn.NAME
                FROM MENU mn
                WHERE mn.DEL_YN = :delYn
                    AND mn.SITE_TYPE = :siteType
                ORDER BY mn.REG_DATE ASC
            """, nativeQuery = true)
    List<IMenuResponse> getListParent(@Param ("siteType") String siteType, @Param("delYn") String delYn);

    @Query (value = """
            SELECT COUNT(*) FROM MENU
            WHERE ROUTE = :route
            AND DEL_YN = :delYn
            AND (:id IS NULL OR :id = '' OR ID <> :id)
            """, nativeQuery = true)
    int checkExistRouteInMenu(@Param ("route") String route, @Param("delYn") String delYn, @Param ("id") Integer id);

    List<MenuEntity> findAllByParentId(Integer parentId);

}
