package be.rentvehicle.service.mapper;

import be.rentvehicle.domain.Roles;
import be.rentvehicle.domain.Town;
import be.rentvehicle.domain.User;
import be.rentvehicle.service.dto.RoleDTO;
import be.rentvehicle.service.dto.TownDTO;
import be.rentvehicle.service.dto.UserInfoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public non-sealed interface TownMapper  extends EntityMapper<TownDTO, Town> {

    TownDTO toDto(Town town);

    Town toEntity(TownDTO townDTO);
}
