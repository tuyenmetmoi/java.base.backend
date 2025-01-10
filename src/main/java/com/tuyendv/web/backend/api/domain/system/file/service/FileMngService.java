package com.tuyendv.web.backend.api.domain.system.file.service;

import com.tuyendv.web.backend.api.dto.system.file.FileMngDto;
import com.tuyendv.web.backend.api.dto.system.file.MultipleFileDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileMngService {

    FileMngDto uploadFile(MultipartFile file, String folderName) throws IOException;

    List<FileMngDto> createNewFile(List<FileMngDto> listFileMngDto);

    Resource downloadSingleFile(String encodeFileName) throws IOException;

    Resource downloadMultipleFile(MultipleFileDTO multipleFileDTO) throws IOException;

    String deleteFileByEncodeName(String encodeFileName) throws IOException;

    void deleteDownloadFolders() throws IOException;

}
