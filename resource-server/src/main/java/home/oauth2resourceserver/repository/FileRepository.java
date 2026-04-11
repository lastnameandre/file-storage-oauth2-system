package home.oauth2resourceserver.repository;

import home.oauth2resourceserver.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByOwnerId(String ownerId);
    Optional<FileEntity> findByOwnerIdAndId(String ownerId, Long id);
}
