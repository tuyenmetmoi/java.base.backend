package com.tuyendv.web.backend.api.util.system;

import com.tuyendv.web.backend.api.config.file.service.FileMngService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class DownloadFolderCleaner {

    @Autowired
    private FileMngService fileMngService;

    // Cron job run 8h AM every day
    @Scheduled(cron = "0 0 8 * * ?", zone = "Asia/Ho_Chi_Minh")
    public void scheduleDeleteDownloadFolders() {
        try {
            fileMngService.deleteDownloadFolders();
            log.info("Delete download folders");
        } catch (IOException e) {
            log.error("Error deleting download folders: ", e);
        }
    }

}
