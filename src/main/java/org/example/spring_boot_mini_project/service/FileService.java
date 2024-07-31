package org.example.spring_boot_mini_project.service;

import org.example.spring_boot_mini_project.exception.FindNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String saveFile(MultipartFile file) throws IOException , FindNotFoundException;
    Resource getFileByFileName(String fileName) throws IOException , FindNotFoundException;
}
