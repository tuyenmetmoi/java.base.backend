package com.tuyendv.web.backend.api.repository.system.file;

import com.tuyendv.web.backend.api.entity.system.file.FileMngEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileMngRepository extends JpaRepository<FileMngEntity, String> {

    FileMngEntity findByFimFileNameAndDelYn(String fimFileName, String delYn);

    List<FileMngEntity> findByFimReferKeyIdInAndFimFileCategoryInAndDelYn(List<String> listReferKeyId,
            List<String> listCategory, String delYn);

}
