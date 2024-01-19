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

    public String imageUpload(MultipartRequest request) throws IOException {
        log.info("[imageUpload] is executed");
        MultipartFile file = request.getFile("upload");

        // Img Content Type Exception 추가 -> 확장자가 png, jpg.. 가 맞는지
        if (file == null) {
            log.error("[imageUpload] file is NULL");
            throw new IOException("file is empty");
        }

        String filename = file.getOriginalFilename();
        String ext = filename.substring(filename.indexOf("."));

        String uuidFileName = UUID.randomUUID() + ext;

        String localPath = imgLocation + uuidFileName;

        File localFile = new File(localPath);
        file.transferTo(localFile);

        return localPath;
    }
}
