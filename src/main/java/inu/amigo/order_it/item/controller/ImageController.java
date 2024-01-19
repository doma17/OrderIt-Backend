package inu.amigo.order_it.item.controller;

import inu.amigo.order_it.item.service.ImageService;
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

@RequestMapping("/api/img")
@Controller
public class ImageController {

    private final ImageService imageService;

    @Value("${image.path}")
    private String imgLocation;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    @ResponseBody
    public Map<String, Object> imageUpload(@RequestParam("file") MultipartFile multipartFile) throws Exception {

        Map<String, Object> responseData = new HashMap<>();

        try {
            String imgPath = imageService.imageUpload(multipartFile);

            responseData.put("uploaded", true);
            responseData.put("path", imgPath);

        } catch (IOException e) {
            responseData.put("uploaded", false);
            responseData.put("error", e.getMessage());
        }

        return responseData;
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable("filename") String filename) throws MalformedURLException {

        Path imagePath = Paths.get(imgLocation, filename);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, String>> handleIOException(IOException e) {

        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("uploaded", "false");
        errorResponse.put("error", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
