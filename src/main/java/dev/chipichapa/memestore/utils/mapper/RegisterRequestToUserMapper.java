package dev.chipichapa.memestore.utils.mapper;

import dev.chipichapa.memestore.domain.user.User;
import dev.chipichapa.memestore.dto.auth.RegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterRequestToUserMapper{

    User toUser(RegisterRequest request);
}
