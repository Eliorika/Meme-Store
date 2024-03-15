package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.entity.user.Role;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.dto.auth.RegisterRequest;
import dev.chipichapa.memestore.dto.auth.TgRegisterRequest;
import dev.chipichapa.memestore.dto.recommedation.UserRabbitDTO;
import dev.chipichapa.memestore.exception.IllegalArgumentException;
import dev.chipichapa.memestore.repository.UserRepository;
import dev.chipichapa.memestore.service.ifc.AlbumService;
import dev.chipichapa.memestore.service.ifc.RecommendationRabbitProducer;
import dev.chipichapa.memestore.usecase.ifc.UserRegisterUseCase;
import dev.chipichapa.memestore.utils.mapper.RegisterRequestToUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Component
@RequiredArgsConstructor
public class UserRegisterUseCaseImpl implements UserRegisterUseCase {

    private final RegisterRequestToUserMapper registerToUserMapper;

    private final UserRepository userRepository;
    private final AlbumService albumService;

    private final PasswordEncoder passwordEncoder;

    private final RecommendationRabbitProducer recommendationRabbitProducer;

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        User user = new User()
                .setTgId(request.tgId())
                .setUsername(request.username())
                .setDisplayName(request.fullName());

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("User with username = (%s) already exists"
                    .formatted(user.getUsername()));
        }

        user.setPassword(passwordEncoder.encode("tgbot"));
        user.setRoles(Set.of(Role.USER_ROLE));
        userRepository.save(user);
        recommendationRabbitProducer.sendUser(new UserRabbitDTO(user.getId()));
        albumService.addDefaultsGalleriesForUser(user);
    }

    @Override
    @Transactional
    public User registerTg(TgRegisterRequest request) {
        User user = registerToUserMapper.toUser(request);

        int i = 1;
        String newName = user.getUsername();

        while (userRepository.existsByUsername(user.getUsername())) {
            User ex = userRepository.findByUsername(user.getUsername()).orElse(null);
            if (ex != null && ex.getTgId() != null && ex.getTgId().equals(user.getTgId())) {
                return ex;
            }
            String name = newName + i;
            i++;
            user.setUsername(name);
        }

        user.setPassword(passwordEncoder.encode("tgbot"));
        user.setRoles(Set.of(Role.USER_ROLE));

        User savedUser = userRepository.save(user);
        albumService.addDefaultsGalleriesForUser(savedUser);
        recommendationRabbitProducer.sendUser(new UserRabbitDTO(user.getId()));
        return savedUser;
    }
}
