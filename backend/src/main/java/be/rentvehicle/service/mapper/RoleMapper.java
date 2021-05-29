package be.rentvehicle.service.mapper;

import be.rentvehicle.domain.Roles;
import be.rentvehicle.service.dto.RoleDTO;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * Mapper for the entity {@link Roles} and its DTO {@link RoleDTO}.
 */
@Mapper(componentModel = "spring")
public non-sealed interface RoleMapper extends EntityMapper<RoleDTO, Roles> {

    RoleDTO toDto(Roles roles);

    Roles toEntity(RoleDTO roleDTO);

    Set<RoleDTO> toDto(Set<Roles> rolesSet);

    Set<Roles> toEntity(Set<RoleDTO> roleDTOSet);
}
