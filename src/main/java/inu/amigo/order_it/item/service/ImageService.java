package inu.amigo.order_it.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class ImageService {

    @Value("${image.path}")
    private String imgLocation;

    private static final String[] ALLOWED_EXTENSIONS = {".png", ".jpg", ".jpeg"};

    public String imageUpload(MultipartFile file) throws IOException {
        log.info("[imageUpload] is executed");

        // file null check
        if (file == null) {
            log.error("[imageUpload] file is NULL");
            throw new IOException("file is empty");
        }

        String filename = file.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf(".")).toLowerCase();

        // extension check
        if (!isAllowedExtension(ext)) {
            log.error("[imageUpload] Invalid file extension");
            throw new IOException("Invaild file extension");
        }

        String uuidFileName = UUID.randomUUID() + ext;

        File filePath = new File(imgLocation + uuidFileName);
        file.transferTo(filePath);

        return uuidFileName;
    }

    private boolean isAllowedExtension(String extension) {
        for (String allowedExtension : ALLOWED_EXTENSIONS) {
            if (allowedExtension.equals(extension)) {
                return true;
            }
        }
        return false;
    }
}
