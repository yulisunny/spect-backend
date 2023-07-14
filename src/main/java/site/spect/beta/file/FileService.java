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
import java.util.List;
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
        String fileName = file.getOriginalFilename();
        String filePath = folderPath + fileName;
        file.transferTo(new File(filePath));
        List<FileData> existingFileData = fileDataRepo.getFileDataByFileNameAndUser_IdAndState(fileName, userId, FileData.State.CREATED);
        if (existingFileData.size() > 0) {
            for (FileData existingFileDatum : existingFileData) {
                existingFileDatum.setUploadedAt(LocalDateTime.now());
                fileDataRepo.save(existingFileDatum);
            }
        } else {
            SpectUser user = spectUserRepo.getReferenceById(userId);
            FileData fileData = new FileData(user, fileName, LocalDateTime.now());
            fileDataRepo.save(fileData);
        }
        return true;
    }

    public byte[] readFileFromDisk(String fileName, Long userId, Optional<Integer> maybePageNumber) throws IOException {
        List<FileData> fileData = fileDataRepo.getFileDataByFileNameAndUser_IdAndState(fileName, userId, FileData.State.CREATED);
        if (fileData.isEmpty()) {
            return null;
        }
        String folderPath = environment.getProperty("folder-path");
        String filePath = folderPath + fileName;
        File file = new File(filePath);

        if (maybePageNumber.isPresent()) {
            return pdfSinglePageExtractor.extract(file, maybePageNumber.get());
        }

        return Files.readAllBytes(file.toPath());
    }

    public boolean markFileAsDeleted(Optional<String> maybeFileName, Long userId) {
        List<FileData> fileData;
        if (maybeFileName.isPresent()) {
            fileData = fileDataRepo.getFileDataByFileNameAndUser_IdAndState(maybeFileName.get(), userId, FileData.State.CREATED);
        } else {
            fileData = fileDataRepo.getFileDataByUser_IdAndState(userId, FileData.State.CREATED);
        }
        for (FileData filaDatum : fileData) {
            filaDatum.markAsDeleted(LocalDateTime.now());
        }
        fileDataRepo.saveAll(fileData);
        return true;
    }

    public boolean doesUserHaveFiles(Long userId) {
        return fileDataRepo.existsFileDataByUser_Id_AndStateIsNot(userId, FileData.State.DELETED);
    }

}
