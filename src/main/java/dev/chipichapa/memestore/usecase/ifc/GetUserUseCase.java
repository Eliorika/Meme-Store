package dev.chipichapa.memestore.usecase.ifc;

import dev.chipichapa.memestore.domain.entity.user.User;

public interface GetUserUseCase {
    User getByTg(Long tgId);
}
