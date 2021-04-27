package be.rentvehicle.service.mapper;

import be.rentvehicle.domain.Address;
import be.rentvehicle.domain.Town;
import be.rentvehicle.domain.User;
import be.rentvehicle.service.dto.AddressDTO;
import be.rentvehicle.service.dto.TownDTO;
import be.rentvehicle.service.dto.UserInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {TownMapper.class}, imports = {UUID.class})
public non-sealed interface AddressMapper extends EntityMapper<AddressDTO, Address> {

    @Mappings({
            @Mapping(expression = "java(address.getId().toString())", target = "addressId"),
            @Mapping(source = "address.town", target = "townDTO")
    })
    AddressDTO toDto(Address address);

    @Mapping(expression = "java(UUID.fromString(addressDTO.getAddressId()))", target = "id")
    Address toEntity(AddressDTO addressDTO);
}
