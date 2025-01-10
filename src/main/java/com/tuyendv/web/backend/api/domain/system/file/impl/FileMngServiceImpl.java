package com.tuyendv.web.backend.api.domain.system.file.impl;

import com.tuyendv.web.backend.api.common.ApiStatus;
import com.tuyendv.web.backend.api.common.Constants;
import com.tuyendv.web.backend.api.domain.system.file.service.FileMngService;
import com.tuyendv.web.backend.api.config.security.handler.CustomException;
import com.tuyendv.web.backend.api.dto.system.file.FileMngDto;
import com.tuyendv.web.backend.api.dto.system.file.MultipleFileDTO;
import com.tuyendv.web.backend.api.entity.system.file.FileMngEntity;
import com.tuyendv.web.backend.api.repository.system.file.FileMngRepository;
import com.tuyendv.web.backend.api.util.system.SequenceGenerator;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.tuyendv.web.backend.api.common.GlobalEnum.PREFIX_FILE_MNG;
import static com.tuyendv.web.backend.api.common.GlobalEnum.TABLE_FILE_MNG;

@Service
public class FileMngServiceImpl implements FileMngService {

    @Value ("${upload.path}")
    private String uploadPath;

    @Value ("${download.path}")
    private String downloadPath;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileMngRepository fileMngRepository;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    private String encodeFileName(String fileName) {
        String uniqueString = UUID.randomUUID() + fileName;
        return DigestUtils.sha256Hex(uniqueString);
    }

    @Override
    public FileMngDto uploadFile(MultipartFile file, String folderName) throws IOException {
        FileMngDto fileMngDto = new FileMngDto();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        //encode file name
        String originalName = file.getOriginalFilename();
        String[] fileExt = originalName.split("\\.");
        String encodeFileNameWithoutExt = encodeFileName(originalName);
        String encodeFileName = encodeFileNameWithoutExt + "." + fileExt[ fileExt.length - 1 ];

        //set upload directory, create if not exist
        String uploadDirectory = uploadPath + '/' + folderName + '/' + dateFormat.format(new Date());
        Path filePath = Paths.get(uploadDirectory);
        if (! Files.exists(filePath)) {
            Files.createDirectories(filePath);
        }

        //save file to upload directory
        Path filePathSave = filePath.resolve(encodeFileName);
        Files.copy(file.getInputStream(), filePathSave);

        //setter fileMngDto
        fileMngDto.setFimId(sequenceGenerator.getNextSequence(PREFIX_FILE_MNG.toString(), TABLE_FILE_MNG.toString()));
        fileMngDto.setFimFileCategory(folderName);
        fileMngDto.setFimFileName(encodeFileNameWithoutExt);
        fileMngDto.setFimFilePath(uploadDirectory);
        fileMngDto.setFimFileExt(fileExt[ fileExt.length - 1 ]);

        return fileMngDto;
    }

    @Override
    @Transactional
    public List<FileMngDto> createNewFile(List<FileMngDto> listFileMngDto) {
        List<FileMngEntity> listEntity = listFileMngDto.stream().map(item -> {
            FileMngEntity entity = modelMapper.map(item, FileMngEntity.class);
            entity.setDelYn(Constants.STATE_N);

            return entity;
        }).toList();

        fileMngRepository.saveAll(listEntity);

        return listEntity.stream().map(item -> modelMapper.map(item, FileMngDto.class)).toList();
    }

    @Override
    public Resource downloadSingleFile(String encodeFileName) throws IOException {
        FileMngDto fileMngDto = getFileByEncodeFileName(encodeFileName);

        List<FileMngDto> listFileMngDto = new ArrayList<>();
        listFileMngDto.add(fileMngDto);

        return zipAndDownload(listFileMngDto);
    }

    @Override
    public Resource downloadMultipleFile(MultipleFileDTO multipleFileDTO) throws IOException {
        List<FileMngEntity> listEntity = fileMngRepository.findByFimReferKeyIdInAndFimFileCategoryInAndDelYn(
                multipleFileDTO.getListFimReferKeyId(), multipleFileDTO.getListFimFileCategory(), Constants.STATE_N);

        List<FileMngDto> listFileMngDto = listEntity.stream().map(item -> modelMapper.map(item, FileMngDto.class))
                .toList();

        if (listFileMngDto.isEmpty()) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        return zipAndDownload(listFileMngDto);
    }

    @Override
    @Transactional
    public String deleteFileByEncodeName(String encodeFileName) throws IOException {
        FileMngEntity entity = fileMngRepository.findByFimFileNameAndDelYn(encodeFileName, Constants.STATE_N);

        if (entity != null) {
            Path filePath = Paths.get(entity.getFimFilePath(), entity.getFimFileName() + "." + entity.getFimFileExt());
            Files.deleteIfExists(filePath);
            entity.setDelYn(Constants.STATE_Y);
            fileMngRepository.save(entity);

            return entity.getFimId();
        } else {
            throw new CustomException(ApiStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void deleteDownloadFolders() throws IOException {
        //delete all download folder (parent and children), khoinm option 1, chatgpt option 2
        Path filePath = Paths.get(downloadPath);
        Files.walk(filePath).sorted((a, b) -> - a.compareTo(b)).map(Path::toFile).forEach(File::delete);

//        if (Files.exists(filePath)) {
//            try (Stream<Path> paths = Files.walk(filePath)) {
//                paths.sorted(Comparator.reverseOrder())
//                        .map(Path::toFile)
//                        .forEach(file -> {
//                            if (!file.delete()) {
//                                System.err.println("Failed to delete file: " + file.getAbsolutePath());
//                            }
//                        });
//            }
//        } else {
//            System.out.println("Path does not exist: " + downloadPath);
//        }
    }

    private FileMngDto getFileByEncodeFileName(String encodeFileName) {
        FileMngEntity entity = fileMngRepository.findByFimFileNameAndDelYn(encodeFileName, Constants.STATE_N);

        if (entity == null) {
            throw new CustomException(ApiStatus.NOT_FOUND);
        }

        return modelMapper.map(entity, FileMngDto.class);
    }

    private Resource zipAndDownload(List<FileMngDto> listFileMngDto) throws IOException {
        String tempDirectoryPath = createTempDirectory();
        List<Resource> resources = new ArrayList<>();

        for (FileMngDto fileMngDto : listFileMngDto) {
            String encodedFileName = fileMngDto.getFimFileName();
            String fileExt = fileMngDto.getFimFileExt();
            String uploadDirectory = fileMngDto.getFimFilePath();
            Path filePath = Paths.get(uploadDirectory).resolve(encodedFileName + "." + fileExt);

            if (Files.exists(filePath)) {
                Path tempPath = Paths.get(tempDirectoryPath).resolve(encodedFileName + "." + fileExt);
                Files.copy(filePath, tempPath, StandardCopyOption.REPLACE_EXISTING);
                resources.add(new FileSystemResource(tempPath.toFile()));
            } else {
                throw new CustomException(ApiStatus.BAD_REQUEST_FILE_NOT_READ);
            }
        }

        return createZipFile(resources, "DownloadFiles", tempDirectoryPath);
    }

    private String createTempDirectory() throws IOException {
        Path filePath = Paths.get(downloadPath);

        if (! Files.exists(filePath)) {
            Files.createDirectories(filePath);
        }

        String tempDirectoryPath = downloadPath + "/tmp_" + UUID.randomUUID();
        Files.createDirectories(Paths.get(tempDirectoryPath));

        return tempDirectoryPath;
    }

    private Resource createZipFile(List<Resource> listResource, String zipFileName, String tempDirectoryPath) throws
            IOException {
        String zipFilePath = tempDirectoryPath + "/" + zipFileName + ".zip";
        Path zipFile = Paths.get(zipFilePath);

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile))) {
            for (Resource resource : listResource) {
                String fileName = resource.getFilename();
                ZipEntry zipEntry = new ZipEntry(fileName != null ? fileName : "");
                zipOutputStream.putNextEntry(zipEntry);
                StreamUtils.copy(resource.getInputStream(), zipOutputStream);
                zipOutputStream.closeEntry();
            }
        }

        return new FileSystemResource(zipFile.toFile());
    }

}
