package org.example.spring_boot_mini_project.service.ServiceImp;

import org.example.spring_boot_mini_project.exception.FindNotFoundException;
import org.example.spring_boot_mini_project.service.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
    private final Path path = Paths.get("src/main/resources/images");
    @Override
    public String saveFile(MultipartFile file) throws IOException , FindNotFoundException {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String fileExtension = StringUtils.getFilenameExtension(fileName).toLowerCase();
        if ("jpg".equals(fileExtension) || "jpeg".equals(fileExtension) || "png".equals(fileExtension)) {
            fileName = UUID.randomUUID() + "." + fileExtension;
            if (!Files.exists(path)){
                Files.createDirectories(path);
            }
            Files.copy(file.getInputStream(), path.resolve(fileName));

            return fileName;

        } else {
            throw new FindNotFoundException("File must be contain jpg, png, jpeg");
        }
    }
    @Override
    public Resource getFileByFileName(String fileName) throws IOException, FindNotFoundException {
        Path path = Paths.get("src/main/resources/images/" + fileName);
        if (Files.exists(path)) {
            return new ByteArrayResource(Files.readAllBytes(path));
        } else {
            throw new FindNotFoundException("File must be contain jpg, png, jpeg");
        }

    }
}
