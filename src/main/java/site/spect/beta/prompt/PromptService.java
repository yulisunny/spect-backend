package site.spect.beta.prompt;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.spect.beta.user.SpectUser;
import site.spect.beta.user.SpectUserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PromptService {

    private final SpectUserRepository spectUserRepository;
    private final PromptRepository promptRepository;
    public boolean addPromptToHistory(Long userId, String prompt) {
        SpectUser user = spectUserRepository.getReferenceById(userId);
        promptRepository.save(new Prompt(user, prompt, LocalDateTime.now()));
        return true;
    }

    public List<Prompt> getByUserId(Long userId) {
        SpectUser user = spectUserRepository.getReferenceById(userId);
        return promptRepository.getPromptsByUser(user);
    }

}
