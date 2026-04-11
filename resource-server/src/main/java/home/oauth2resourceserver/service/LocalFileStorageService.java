package home.oauth2resourceserver.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalFileStorageService implements FileStorageService {

    private final Path root = Paths.get("uploads");

    public LocalFileStorageService() throws IOException {
        Files.createDirectories(root);
    }

    @Override
    public String save(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path target = root.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return target.toString();
    }

    @Override
    public Resource load(String path) throws MalformedURLException {
        return new UrlResource(Paths.get(path).toUri());
    }

    @Override
    public void delete(String path) throws IOException {
        Files.deleteIfExists(Paths.get(path));
    }

}
