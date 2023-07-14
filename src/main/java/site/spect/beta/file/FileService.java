package site.spect.beta.file;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.spect.beta.user.SpectUser;
import site.spect.beta.user.SpectUserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FileService {

    private SpectUserRepository spectUserRepo;
    private FileDataRepository fileDataRepo;
    private Environment environment;
    private PdfSinglePageExtractor pdfSinglePageExtractor;

    public boolean saveFileToDisk(MultipartFile file, Long userId) throws IOException {
        String folderPath = environment.getProperty("folder-path");
        String filePath = folderPath + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        SpectUser user = spectUserRepo.getReferenceById(userId);
        FileData fileData = new FileData(user, file.getOriginalFilename(), LocalDateTime.now());
        fileDataRepo.save(fileData);
        return true;
    }

    public byte[] readFileFromDisk(String fileName, Optional<Integer> maybePageNumber) throws IOException {
        String folderPath = environment.getProperty("folder-path");
        String filePath = folderPath + fileName;
        File file = new File(filePath);

        if (maybePageNumber.isPresent()) {
            return pdfSinglePageExtractor.extract(file, maybePageNumber.get());
        }

        return Files.readAllBytes(file.toPath());
    }

    public boolean markFileAsDeleted(String fileName, Long userId) {
        FileData fileData = fileDataRepo.getFileDataByFileNameAndUser_Id(fileName, userId);
        fileData.markAsDeleted(LocalDateTime.now());
        fileDataRepo.save(fileData);
        return true;
    }

    public boolean doesUserHaveFiles(Long userId) {
        return fileDataRepo.existsFileDataByUser_Id_AndStateIsNot(userId, FileData.State.DELETED);
    }

}
