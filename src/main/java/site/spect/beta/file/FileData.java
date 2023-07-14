package site.spect.beta.file;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.spect.beta.user.SpectUser;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class FileData {

    @SequenceGenerator(
            name = "file_data_sequence",
            sequenceName = "file_data_sequence",
            allocationSize = 1
    )

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "file_data_sequence"
    )
    private Long id;
    @ManyToOne
    private SpectUser user;
    private String fileName;
    private State state;
    private LocalDateTime uploadedAt;
    private LocalDateTime deletedAt;

    public enum State {
        CREATED,
        DELETED
    }

    public FileData(SpectUser user, String fileName, LocalDateTime uploadedAt) {
        this.user = user;
        this.fileName = fileName;
        this.state = State.CREATED;
        this.uploadedAt = uploadedAt;
    }

    public void markAsDeleted(LocalDateTime when) {
        this.state = State.DELETED;
        this.deletedAt = when;
    }

}
