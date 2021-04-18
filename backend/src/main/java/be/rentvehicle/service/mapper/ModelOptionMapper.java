package be.rentvehicle.service.mapper;

import be.rentvehicle.domain.ModelOption;
import be.rentvehicle.service.dto.ModelOptionDTO;
import org.mapstruct.*;


/**
 * Mapper for the entity {@link ModelOption} and its DTO {@link ModelOptionDTO}.
 */
@Mapper(componentModel = "spring")
public non-sealed interface ModelOptionMapper  extends EntityMapper<ModelOptionDTO, ModelOption>  {

    @Mappings({
            @Mapping(source = "seatsNumber", target = "seatsNumber"),
            @Mapping(source = "hasAirConditioner", target = "hasAirConditioner"),
            @Mapping(source = "isAutomatic", target = "isAutomatic"),
            @Mapping(source = "bagsNumber", target = "bagsNumber")
    })
    ModelOption toEntity(ModelOptionDTO modelOptionDTO);

    @InheritInverseConfiguration(name = "toEntity")
    ModelOptionDTO toDto(ModelOption modelOption);

    // List<ModelOptionDTO> toDto(List<ModelOption> modelOptions);

   // List<ModelOption> toEntity(List<ModelOptionDTO> modelOptionDTOS);

    default ModelOption fromId(String id) {
        if (id == null) {
            return null;
        }
        ModelOption modelOption = new ModelOption();
        modelOption.setOptionCode(id);
        return modelOption;
    }
}
