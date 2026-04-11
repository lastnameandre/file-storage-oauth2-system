package home.oauth2resourceserver.controller;

import home.oauth2resourceserver.model.FileEntity;
import home.oauth2resourceserver.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;


    @PostMapping("/upload")
    public ResponseEntity<FileEntity> upload(@RequestParam MultipartFile file, Authentication auth) throws IOException {
        String ownerId = auth.getName();
        FileEntity saved = fileService.upload(file, ownerId);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<FileEntity> listFiles(Authentication auth) {

        return fileService.getMyFiles(auth.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, Authentication auth) throws IOException {
        fileService.deleteFile(id, auth.getName());
        return ResponseEntity.ok("Deleted");
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Long id, Authentication auth) throws IOException {
        return fileService.download(id, auth.getName());
    }
    @PostMapping("/{id}/share")
    public ResponseEntity<String> share(@PathVariable Long id, Authentication auth) {

        String token = fileService.createShareLink(id, auth.getName());

        String url = "http://localhost:8081/public/" + token;

        return ResponseEntity.ok(url);
    }
}
