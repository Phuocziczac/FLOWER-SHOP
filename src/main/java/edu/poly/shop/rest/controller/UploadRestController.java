package edu.poly.shop.rest.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.poly.shop.service.UploadService;

@CrossOrigin("*")
@RestController
public class UploadRestController {
    @Autowired
    UploadService uploadService;

    @PostMapping("rest/upload/{folder}")
    public JsonNode upload(@RequestParam("file") MultipartFile file, @PathVariable("folder") String folder) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        try {
            File savedFile = uploadService.save(file, folder);
            node.put("name", savedFile.getName());
            node.put("size", savedFile.length());
        } catch (Exception e) {
            node.put("error", e.getMessage());
        }
        return node;
    }
}
