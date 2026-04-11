package home.oauth2client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;


@Controller
public class FileController {
    private final RestTemplate restTemplate;

    public FileController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/files")
    public String listFiles(Model model) {

        ResponseEntity<List<FileDto>> response =
                restTemplate.exchange(
                        "http://localhost:8081/api/files",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<FileDto>>() {}
                );

        model.addAttribute("files", response.getBody());
        return "files-view";
    }
    @PostMapping("/files/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(body, headers);

        restTemplate.postForEntity(
                "http://localhost:8081/api/files/upload",
                requestEntity,
                String.class
        );

        return "redirect:/files";
    }

    @PostMapping("/files/delete/{id}")
    public String deleteFile(@PathVariable Long id) {

        restTemplate.exchange(
                "http://localhost:8081/api/files/" + id,
                HttpMethod.DELETE,
                null,
                String.class
        );

        return "redirect:/files";
    }

    @GetMapping("/files/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {

        ResponseEntity<byte[]> response =
                restTemplate.exchange(
                        "http://localhost:8081/api/files/download/" + id,
                        HttpMethod.GET,
                        null,
                        byte[].class
                );

        return ResponseEntity.ok()
                .headers(response.getHeaders())
                .body(response.getBody());
    }
    @PostMapping("/files/share/{id}")
    public String shareFile(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        String url = restTemplate.postForObject(
                "http://localhost:8081/api/files/" + id + "/share",
                null,
                String.class
        );

        redirectAttributes.addFlashAttribute("shareLink", url);

        return "redirect:/files";
    }
}