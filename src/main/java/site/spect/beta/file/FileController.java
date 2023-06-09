package site.spect.beta.file;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping(path = "api/v1/file")
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    public boolean upload(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId)
            throws IOException {
        return fileService.saveFileToDisk(file, userId);
    }

    @GetMapping
    public ResponseEntity<?> download(@RequestParam("fileName") String fileName, @RequestParam("userId") Long userId,
                                      @RequestParam("pageNumber") Optional<Integer> maybePageNumber) throws IOException {
        byte[] fileData = fileService.readFileFromDisk(fileName, userId, maybePageNumber);
        if (fileData == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .body(fileData);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestParam("userId") Long userId, @RequestParam("fileName") Optional<String> maybeFileName) {
        return fileService.markFileAsDeleted(maybeFileName, userId);
    }

    @GetMapping("/doesUserHaveFiles")
    public boolean doesUserHaveFiles(@RequestParam("userId") Long userId) {
        return fileService.doesUserHaveFiles(userId);
    }

}
