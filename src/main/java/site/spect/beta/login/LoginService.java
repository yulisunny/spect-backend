package site.spect.beta.login;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import site.spect.beta.user.SpectUser;
import site.spect.beta.user.SpectUserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    private final SpectUserRepository spectUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<Long> getUserIdIfCredentialsValid(LoginRequest loginRequest) {
        Optional<SpectUser> maybeSpectUser = spectUserRepository.findByEmail(loginRequest.getEmail());
        if (maybeSpectUser.isPresent()) {
            SpectUser user = maybeSpectUser.orElseThrow();
            String encryptedPass = user.getPassword();
            if (bCryptPasswordEncoder.matches(loginRequest.getPassword(), encryptedPass)) {
                return Optional.of(user.getId());
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

}
