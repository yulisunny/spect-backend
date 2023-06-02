package site.spect.beta.prompt;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PromptView {
    private final String prompt;
    private final LocalDateTime createdAt;
}
