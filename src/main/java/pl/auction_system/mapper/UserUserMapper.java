package pl.auction_system.mapper;

import org.mapstruct.Mapper;
import pl.auction_system.dto.UserUserDto;
import pl.auction_system.model.User;

@Mapper(componentModel = "spring")
public interface UserUserMapper {
    UserUserDto toDto(User user);
}
