package site.spect.beta.file;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class FileGetRequest {

    private final String fileName;
    private final Long userId;

}
