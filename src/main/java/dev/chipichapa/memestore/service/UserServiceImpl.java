package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.exception.ResourceNotFoundException;
import dev.chipichapa.memestore.repository.UserRepository;
import dev.chipichapa.memestore.service.ifc.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    String errorMessage = String.format("User with username: %s -- is not found", username);
                    return new ResourceNotFoundException(errorMessage);
                });
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            String errorMessage = String.format("User with id = %d not found", id);
            return new ResourceNotFoundException(errorMessage);
        });
    }

    public User getByTgId(Long tgId){
        return userRepository.findByTgId(tgId)
                .orElseThrow(() -> {
                    String errorMessage = String.format("User with tg id == (%d) is not found", tgId);
                    return new ResourceNotFoundException(errorMessage);
                });
    }
}
