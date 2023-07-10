package site.spect.beta.file;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.spect.beta.FileService;

import java.io.IOException;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping(path = "api/v1/file")
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    public boolean upload(@RequestParam("file") MultipartFile file, @RequestParam("user_id") Long userId)
            throws IOException {
        return fileService.saveFileToDisk(file);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> download(@PathVariable String fileName) throws IOException {
        byte[] fileData = fileService.readFileFromDisk(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileData);
    }

}