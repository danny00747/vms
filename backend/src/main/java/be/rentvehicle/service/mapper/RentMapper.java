package be.rentvehicle.service.mapper;

import be.rentvehicle.domain.Rent;
import be.rentvehicle.service.dto.RentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper for the entity {@link Rent} and its DTO {@link RentDTO}.
 */
@Mapper(componentModel = "spring")
public non-sealed interface RentMapper extends EntityMapper<RentDTO, Rent> {

    @Mappings({
            @Mapping(expression = "java(rent.getId().toString())", target = "rentId"),
    })
    RentDTO toDto(Rent rent);

    @Mappings({
            @Mapping(target = "id", ignore = true),
    })
    Rent toEntity(RentDTO rentDTO);
}
