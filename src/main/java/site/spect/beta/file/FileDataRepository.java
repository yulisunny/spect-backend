package site.spect.beta.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface FileDataRepository extends JpaRepository<FileData, Long> {

    boolean existsFileDataByUser_Id(Long userId);

}
