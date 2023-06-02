package site.spect.beta.prompt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import site.spect.beta.user.SpectUser;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PromptRepository extends JpaRepository<Prompt, Long> {

    List<Prompt> getPromptsByUser(SpectUser user);

}
