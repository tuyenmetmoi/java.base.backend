package com.tuyendv.web.backend.api.domain.system.file.web;

import com.tuyendv.web.backend.api.common.ApiResponse;
import com.tuyendv.web.backend.api.common.ApiStatus;
import com.tuyendv.web.backend.api.domain.system.file.service.FileMngService;
import com.tuyendv.web.backend.api.config.security.handler.CustomException;
import com.tuyendv.web.backend.api.dto.system.file.FileMngDto;
import com.tuyendv.web.backend.api.dto.system.file.MultipleFileDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping ("/cmm/files")
public class FileMngController {

    // List ALLOWED EXTENSIONS of file
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".txt", ".doc", ".docx", ".xls", ".xlsx",
            ".ppt", ".pptx", ".pdf", ".jpg", ".png", ".jpeg");

    @Value ("${upload.path}")
    private String uploadPath;

    @Autowired
    private FileMngService fileMngService;

    //PostConstruct: call method after object is instantiated and dependency injection is complete
    @PostConstruct
    public void init() {
        try {
            //toAbsolutePath(): convert url to absolute
            //normalize(): remove extra parts (. or ..)
            Path fileStoreLocal = Paths.get(uploadPath).toAbsolutePath().normalize();
            Files.createDirectories(fileStoreLocal); //create folder if not exist
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    //use RequestParam, default required = true => lstFile != null
    @PostMapping (value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<List<FileMngDto>> uploadFile(@RequestParam ("file") MultipartFile[] listFile,
            @RequestParam ("category") String category, @RequestParam ("referKeyId") String referKeyId) throws
            IOException {
        if (listFile.length == 1 && listFile[ 0 ].getOriginalFilename().isEmpty()) {
            throw new CustomException(ApiStatus.BAD_REQUEST_FILE_EMPTY);
        }

        List<FileMngDto> listFileMngDto = new ArrayList<>();

        for (MultipartFile file : listFile) {
            String fileExtension = getFileExtension(file.getOriginalFilename());

            if (! ALLOWED_EXTENSIONS.contains(fileExtension)) {
                throw new CustomException(ApiStatus.BAD_REQUEST_FILE_EXTENSIONS);
            }

            FileMngDto fileMngDto = fileMngService.uploadFile(file, category);
            fileMngDto.setFimReferKeyId(referKeyId);

            listFileMngDto.add(fileMngDto);
        }

        List<FileMngDto> listResult = fileMngService.createNewFile(listFileMngDto);

        return new ApiResponse<>(ApiStatus.SUCCESS, listResult);
    }

    @PostMapping ("/download")
    public ResponseEntity<Resource> downloadFile(@RequestBody String encodeFileName) throws IOException {
        if (encodeFileName.isEmpty()) {
            throw new CustomException(ApiStatus.BAD_REQUEST_FILE_EMPTY);
        }

        Resource resource = fileMngService.downloadSingleFile(encodeFileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .contentLength(resource.getFile().length()).contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping ("/downloadMultiple")
    public ResponseEntity<Resource> downloadMultipleFile(@RequestBody MultipleFileDTO multipleFileDTO) throws
            IOException {
        if (multipleFileDTO.getListFimReferKeyId().isEmpty() || multipleFileDTO.getListFimFileCategory().isEmpty()) {
            throw new CustomException(ApiStatus.BAD_REQUEST_FILE_EMPTY);
        }

        Resource resource = fileMngService.downloadMultipleFile(multipleFileDTO);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .contentLength(resource.getFile().length()).contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PutMapping("/delete/{encodeFileName}")
    public ApiResponse<String> deleteFileByEncodeName(@PathVariable String encodeFileName) throws IOException {
        String deleteFileId = fileMngService.deleteFileByEncodeName(encodeFileName);

        return new ApiResponse<>(ApiStatus.SUCCESS, deleteFileId);
    }

}
