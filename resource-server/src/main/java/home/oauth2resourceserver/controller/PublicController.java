package home.oauth2resourceserver.controller;

import home.oauth2resourceserver.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final FileService fileService;

    public PublicController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{token}")
    public ResponseEntity<Resource> publicDownload(@PathVariable String token) throws IOException {
        return fileService.downloadByToken(token);
    }
}
