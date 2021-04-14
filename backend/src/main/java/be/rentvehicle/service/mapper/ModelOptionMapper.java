package be.rentvehicle.service.mapper;

import be.rentvehicle.domain.ModelOption;
import be.rentvehicle.service.dto.ModelOptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


/**
 * Mapper for the entity {@link ModelOption} and its DTO {@link ModelOptionDTO}.
 */
@Mapper(componentModel = "spring")
public non-sealed interface ModelOptionMapper  extends EntityMapper<ModelOptionDTO, ModelOption>  {

    @Mappings({
            @Mapping(source = "seatsNumber", target = "seatsNumber"),
            @Mapping(source = "hasAirConditioner", target = "hasAirConditioner"),
            @Mapping(source = "bagsNumber", target = "bagsNumber"),
            @Mapping(source = "isAutomatic", target = "isAutomatic")
    })
    ModelOption toEntity(ModelOptionDTO modelOptionDTO);

    @Mappings({
            @Mapping(source = "seatsNumber", target = "seatsNumber"),
            @Mapping(source = "hasAirConditioner", target = "hasAirConditioner"),
            @Mapping(source = "bagsNumber", target = "bagsNumber"),
            @Mapping(source = "isAutomatic", target = "isAutomatic")
    })
    ModelOptionDTO toDto(ModelOption modelOption);

    default ModelOption fromId(String id) {
        if (id == null) {
            return null;
        }
        ModelOption modelOption = new ModelOption();
        modelOption.setOptionCode(id);
        return modelOption;
    }
}
