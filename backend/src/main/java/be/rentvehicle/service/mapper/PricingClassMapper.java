package be.rentvehicle.service.mapper;

import be.rentvehicle.domain.PricingClass;
import be.rentvehicle.domain.User;
import be.rentvehicle.service.dto.PricingClassDTO;
import be.rentvehicle.service.dto.UserInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;


@Mapper(componentModel = "spring")
public non-sealed interface PricingClassMapper extends EntityMapper<PricingClassDTO, PricingClass> {

    PricingClassDTO toDto(PricingClass pricingClass);

    PricingClass toEntity(PricingClassDTO pricingClassDTO);

    List<PricingClassDTO> toDto(List<PricingClass> pricingClassList);

    List<PricingClass> toEntity(List<PricingClassDTO> pricingClassDTOList);

    @Mapping(target = "className", ignore = true)
    void partialUpdate(@MappingTarget PricingClass pricingClass, PricingClassDTO pricingClassDTO);

}
