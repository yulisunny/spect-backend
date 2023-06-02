package site.spect.beta.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.spect.beta.user.UserRole;
import site.spect.beta.user.SpectUserService;
import site.spect.beta.user.SpectUser;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final SpectUserService spectUserService;

    public Long register(RegistrationRequest request) {
        Long id = spectUserService.signUpUser(
                new SpectUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER

                )
        );
        return id;
    }

}
