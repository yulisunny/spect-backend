package site.spect.beta.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface FileDataRepository extends JpaRepository<FileData, Long> {

    boolean existsFileDataByUser_Id_AndStateIsNot(Long userId, FileData.State state);

    List<FileData> getFileDataByUser_IdAndState(Long userId, FileData.State state);

    List<FileData> getFileDataByFileNameAndUser_IdAndState(String fileName, Long userId, FileData.State state);

}
