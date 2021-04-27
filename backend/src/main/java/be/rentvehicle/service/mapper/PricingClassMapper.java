package be.rentvehicle.service.mapper;

import be.rentvehicle.domain.PricingClass;
import be.rentvehicle.service.dto.PricingClassDTO;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public non-sealed interface PricingClassMapper extends EntityMapper<PricingClassDTO, PricingClass> {

    PricingClassDTO toDto(PricingClass pricingClass);

    PricingClass toEntity(PricingClassDTO pricingClassDTO);

    List<PricingClassDTO> toDto(List<PricingClass> pricingClassList);

    List<PricingClass> toEntity(List<PricingClassDTO> pricingClassDTOList);

    default PricingClass fromId(String name) {
        if (name == null) {
            return null;
        }
        PricingClass pricingClass = new PricingClass();
        pricingClass.setClassName(name);
        return pricingClass;
    }

}
