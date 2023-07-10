package site.spect.beta.file;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@AllArgsConstructor
public class FileService {

    private static String folderPath = "/Users/senyu/";

    public boolean saveFileToDisk(MultipartFile file) throws IOException {
        String filePath = folderPath+file.getOriginalFilename();
        file.transferTo(new File(filePath));
        return true;
    }

    public byte[] readFileFromDisk(String fileName) throws IOException {
        String filePath = folderPath + fileName;
        return Files.readAllBytes(new File(filePath).toPath());
    }

}
