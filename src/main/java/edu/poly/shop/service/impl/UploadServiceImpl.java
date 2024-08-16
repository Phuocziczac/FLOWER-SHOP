package edu.poly.shop.service.impl;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.poly.shop.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    ServletContext app;

    @Override
    public File save(MultipartFile file, String folder) {
        // Tạo thư mục nếu chưa tồn tại
    	File dir = new File(app.getRealPath("/images/uploads/" + folder));
    	if (!dir.exists()) {
    	    dir.mkdirs();  // Sử dụng mkdirs() để tạo toàn bộ thư mục nếu không tồn tại
    	}


        // Tạo tên file duy nhất
        String s = System.currentTimeMillis() + file.getOriginalFilename();
        String name = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));
        try {
            // Lưu file
            File savedFile = new File(dir, name);
            file.transferTo(savedFile);
            System.out.println(savedFile.getAbsolutePath());
            return savedFile;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
