package be.rentvehicle.service.mapper;

import be.rentvehicle.domain.Model;
import be.rentvehicle.domain.Town;
import be.rentvehicle.domain.User;
import be.rentvehicle.service.dto.ModelDTO;
import be.rentvehicle.service.dto.TownDTO;
import be.rentvehicle.service.dto.UserInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {BookingMapper.class, AddressMapper.class, RoleMapper.class}, imports = {UUID.class})
public non-sealed interface UserInfoMapper extends EntityMapper<UserInfoDTO, User> {

    @Mappings({
            @Mapping(expression = "java(user.getId().toString())", target = "userId"),
            @Mapping(source = "email", target = "userEmail"),
            @Mapping(source = "user.roles", target = "userRoles"),
            @Mapping(source = "user.address", target = "addressDTO"),
            @Mapping(source = "user.booking", target = "bookingDTO")
    })
    UserInfoDTO toDto(User user);

    @Mapping(expression = "java(UUID.fromString(userInfoDTO.getUserId()))", target = "id")
    User toEntity(UserInfoDTO userInfoDTO);
}
