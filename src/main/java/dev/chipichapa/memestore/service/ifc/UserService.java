package dev.chipichapa.memestore.service.ifc;

import dev.chipichapa.memestore.domain.user.User;

public interface UserService {

    User getByUsername(String username);

    User getById(Long id);
}
