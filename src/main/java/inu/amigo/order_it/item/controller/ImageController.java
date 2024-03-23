package inu.amigo.order_it.item.controller;

import inu.amigo.order_it.item.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Image API")
@RequestMapping
@Controller
public class ImageController {

    private final ImageService imageService;

    // 로컬 이미지 저장 경로
    @Value("${image.path}")
    private String imgLocation;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * 이미지 업로드를 처리하는 엔드포인트
     *
     * @param multipartFile 업로드된 이미지 파일
     * @return 업로드 결과에 대한 응답
     * @throws Exception 이미지 업로드 중 발생한 예외
     */
    @Operation(summary = "이미지 업로드")
    @PostMapping(value = "/api/img/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> imageUpload(
            @Parameter(
                    name = "file",
                    description = "업로드할 이미지 파일",
                    required = true
            )
            @RequestParam("file") MultipartFile multipartFile) throws IOException {

        Map<String, Object> responseData = new HashMap<>();

        String imgPath = imageService.imageUpload(multipartFile);

        responseData.put("uploaded", true);
        responseData.put("path", imgPath);

        return responseData;
    }

    /**
     * 이미지 조회를 처리하는 엔드포인트
     *
     * @param filename 조회할 이미지 파일 이름
     * @return 조회된 이미지 파일 리소스 및 응답 상태
     * @throws MalformedURLException 이미지 파일의 URL 생성 중 발생한 예외
     */
    @Operation(summary = "이미지 조회")
    @GetMapping(value = "/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getImage(
            @Parameter(name = "filename", description = "조회할 이미지 파일 이름", required = true)
            @PathVariable("filename") String filename) throws MalformedURLException {

        Path imagePath = Paths.get(imgLocation, filename);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * 이미지 업로드 또는 조회 중 발생한 IOException을 처리하는 핸들러 메소드
     *
     * @param e 이미지 업로드 또는 조회 중 발생한 IOException
     * @return 에러 응답
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, String>> handleIOException(IOException e) {

        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("uploaded", "false");
        errorResponse.put("error", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
