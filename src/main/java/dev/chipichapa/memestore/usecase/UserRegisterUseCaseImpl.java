package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.entity.user.Role;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.dto.auth.RegisterRequest;
import dev.chipichapa.memestore.dto.auth.TgRegisterRequest;
import dev.chipichapa.memestore.exception.IllegalArgumentException;
import dev.chipichapa.memestore.repository.UserRepository;
import dev.chipichapa.memestore.usecase.ifc.UserRegisterUseCase;
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

    @Override
    public User registerTg(TgRegisterRequest request) {
        User user = registerToUserMapper.toUser(request);

        int i = 1;
        String newName = user.getUsername();

        while (userRepository.existsByUsername(user.getUsername())) {
            User ex = userRepository.findByUsername(user.getUsername()).orElse(null);
            if(ex != null && ex.getTgId()!= null && ex.getTgId().equals(user.getTgId())){
                return ex;
            }
            String name = newName + i;
            i++;
            user.setUsername(name);
        }

        user.setPassword(passwordEncoder.encode("tgbot"));
        user.setRoles(Set.of(Role.USER_ROLE));
        return userRepository.save(user);
    }
}
