package home.oauth2resourceserver.service;

import home.oauth2resourceserver.model.FileEntity;
import home.oauth2resourceserver.model.ShareLink;
import home.oauth2resourceserver.repository.FileRepository;
import home.oauth2resourceserver.repository.ShareLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FileStorageService storageService;
    private final ShareLinkRepository shareLinkRepository;

    public FileEntity upload(MultipartFile file, String ownerId) throws IOException {
        String path = storageService.save(file);
        FileEntity entity = new FileEntity();
        entity.setName(file.getOriginalFilename());
        entity.setStoragePath(path);
        entity.setSize(file.getSize());
        entity.setContentType(file.getContentType());
        entity.setOwnerId(ownerId);
        entity.setCreatedAt(LocalDateTime.now());

        return fileRepository.save(entity);
    }

    public List<FileEntity> getMyFiles(String ownerId) {
        return fileRepository.findByOwnerId(ownerId);
    }

    public void deleteFile(Long id, String ownerId) throws IOException {
        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        if (!file.getOwnerId().equals(ownerId)) {
            throw new AccessDeniedException("Not your file");
        }

        storageService.delete(file.getStoragePath());
        fileRepository.delete(file);
    }
    public ResponseEntity<Resource> download(Long id, String ownerId) throws IOException {
        FileEntity fileEntity = fileRepository.findByOwnerIdAndId(ownerId, id).orElseThrow();
        Resource resource = storageService.load(fileEntity.getStoragePath());
        String encodedFilename = URLEncoder.encode(fileEntity.getName(), StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"").body(resource);
    }
    public String createShareLink(Long fileId, String ownerId) {
        FileEntity file = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        if (!file.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("Access denied");
        }

        ShareLink link = new ShareLink();
        link.setFileId(fileId);
        link.setToken(UUID.randomUUID().toString());
        link.setCreatedAt(LocalDateTime.now());

        shareLinkRepository.save(link);

        return link.getToken();
    }

    public ResponseEntity<Resource> downloadByToken(String token) throws IOException {

        ShareLink link = shareLinkRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid link"));

        FileEntity file = fileRepository.findById(link.getFileId())
                .orElseThrow(() -> new RuntimeException("File not found"));

        Path path = Paths.get(file.getStoragePath());
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("File not found on disk");
        }

        String filename = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }
}
