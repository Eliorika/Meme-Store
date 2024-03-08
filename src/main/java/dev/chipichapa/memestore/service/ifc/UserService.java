package dev.chipichapa.memestore.service.ifc;

import dev.chipichapa.memestore.domain.entity.user.User;

public interface UserService {

    User getByUsername(String username);

    User getById(Long id);

    User getByTgId(Long id);
}
