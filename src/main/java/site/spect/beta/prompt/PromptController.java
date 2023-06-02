package site.spect.beta.prompt;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/prompt")
@AllArgsConstructor
public class PromptController {

    private final PromptService promptService;

    @PostMapping(path = "add")
    public boolean addPromptToHistory(@RequestBody AddPromptToHistoryRequest request) {
        return promptService.addPromptToHistory(request.getUserId(), request.getPrompt());
    }


    @GetMapping(path = "get")
    public List<PromptView> getByUserId(@RequestParam("userId") Long userId) {
        return promptService.getByUserId(userId).stream()
                .map(prompt -> new PromptView(prompt.getPrompt(), prompt.getCreatedAt()))
                .collect(Collectors.toList());
    }

}
