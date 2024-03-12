package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.repository.UserRepository;
import dev.chipichapa.memestore.usecase.ifc.GetUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {
    private final UserRepository userRepository;
    public User getByTg(Long tgId){
        return userRepository.findByTgId(tgId).orElse(null);
    }
}
