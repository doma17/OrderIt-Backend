package inu.amigo.order_it.item.controller;

import inu.amigo.order_it.item.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/img")
@Controller
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    @ResponseBody
    public Map<String, Object> imageUpload(MultipartRequest request) throws Exception {

        Map<String, Object> responseData = new HashMap<>();

        try {
            String imgUrl = imageService.imageUpload(request);

            responseData.put("uploaded", true);
            responseData.put("url", imgUrl);

        } catch (IOException e) {

            responseData.put("uploaded", false);
        }


        return responseData;
    }
}
