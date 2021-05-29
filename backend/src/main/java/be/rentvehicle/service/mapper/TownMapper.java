package be.rentvehicle.service.mapper;

import be.rentvehicle.domain.Town;
import be.rentvehicle.service.dto.TownDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Town} and its DTO {@link TownDTO}.
 */
@Mapper(componentModel = "spring")
public non-sealed interface TownMapper  extends EntityMapper<TownDTO, Town> {

    TownDTO toDto(Town town);

    Town toEntity(TownDTO townDTO);
}
