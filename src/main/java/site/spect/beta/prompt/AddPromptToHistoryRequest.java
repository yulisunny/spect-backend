package site.spect.beta.prompt;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddPromptToHistoryRequest {
    private final Long userId;
    private final String prompt;
}
