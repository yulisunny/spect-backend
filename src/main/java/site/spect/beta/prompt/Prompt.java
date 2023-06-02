package site.spect.beta.prompt;

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
public class Prompt {

    @SequenceGenerator(
            name = "prompt_sequence",
            sequenceName = "prompt_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "prompt_sequence"
    )
    private Long id;
    @ManyToOne
    private SpectUser user;
    private String prompt;
    private LocalDateTime createdAt;

    public Prompt(SpectUser user, String prompt, LocalDateTime createdAt) {
        this.user = user;
        this.prompt = prompt;
        this.createdAt = createdAt;
    }

}
