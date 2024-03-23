package inu.amigo.order_it.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 이미지 업로드 서비스 클래스
 */
@Slf4j
@Service
public class ImageService {

    // 로컬 이미지 저장 경로
    @Value("${image.path}")
    private String imgLocation;

    private static final String[] ALLOWED_EXTENSIONS = {".png", ".jpg", ".jpeg"};

    /**
     * 업로드된 이미지 파일을 저장하고 해당 파일의 UUID 기반 고유한 이름을 반환합니다.
     *
     * @param file 업로드된 이미지 파일
     * @return 저장된 이미지 파일의 UUID 기반 고유한 이름
     * @throws IOException 이미지 파일 업로드 중 발생한 예외
     */
    public String imageUpload(MultipartFile file) throws IOException {
        log.info("[imageUpload] is executed");

        // file null check
        if (file == null) {
            log.error("[imageUpload] file is NULL");
            throw new IOException("file is empty");
        }

        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();

        // extension check
        if (!isAllowedExtension(ext)) {
            log.error("[imageUpload] Invalid file extension");
            throw new IOException("Invaild file extension");
        }

        // (e.g.) -> (uuid)_filename.extension
        String uuidFileName = UUID.randomUUID() + "_" + originalFilename;

        File filePath = new File(imgLocation + uuidFileName);
        file.transferTo(filePath);
        log.info("[imageUpload] uuidFileName = {}", uuidFileName);

        return uuidFileName;
    }

    /**
     * 허용된 이미지 확장자인지 확인합니다.
     *
     * @param extension 확인할 확장자
     * @return 허용된 확장자인 경우 true, 그렇지 않으면 false
     */
    private boolean isAllowedExtension(String extension) {
        for (String allowedExtension : ALLOWED_EXTENSIONS) {
            if (allowedExtension.equals(extension)) {
                return true;
            }
        }
        return false;
    }
}
