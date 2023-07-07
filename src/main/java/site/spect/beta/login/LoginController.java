package site.spect.beta.login;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public Long login(@RequestBody LoginRequest loginRequest) {
        Optional<Long> maybeUserId = loginService.getUserIdIfCredentialsValid(loginRequest);
        return maybeUserId.orElse(-1L);
    }
}
