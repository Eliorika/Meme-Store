package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.user.Role;
import dev.chipichapa.memestore.domain.user.User;
import dev.chipichapa.memestore.dto.auth.RegisterRequest;
import dev.chipichapa.memestore.exception.IllegalArgumentException;
import dev.chipichapa.memestore.repository.UserRepository;
import dev.chipichapa.memestore.utils.mapper.RegisterRequestToUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
@RequiredArgsConstructor
public class UserRegisterUseCaseImpl implements UserRegisterUseCase {

    private final RegisterRequestToUserMapper registerToUserMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest request) {
        User user = registerToUserMapper.toUser(request);

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(Role.USER_ROLE));
        userRepository.save(user);
    }
}
