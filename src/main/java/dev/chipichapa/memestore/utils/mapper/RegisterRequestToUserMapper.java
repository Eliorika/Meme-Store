package dev.chipichapa.memestore.utils.mapper;

import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.dto.auth.RegisterRequest;
import dev.chipichapa.memestore.dto.auth.TgRegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterRequestToUserMapper{

    User toUser(RegisterRequest request);
    User toUser(TgRegisterRequest request);
}
