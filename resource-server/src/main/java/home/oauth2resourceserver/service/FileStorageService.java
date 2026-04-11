package home.oauth2resourceserver.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface FileStorageService {

    String save(MultipartFile file) throws IOException;

    Resource load(String path) throws MalformedURLException;

    void delete(String path) throws IOException;

}
