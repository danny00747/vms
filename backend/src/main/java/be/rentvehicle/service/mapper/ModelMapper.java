package be.rentvehicle.service.mapper;

import be.rentvehicle.domain.Model;
import be.rentvehicle.service.dto.ModelDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.UUID;

/**
 * Mapper for the entity {@link Model} and its DTO {@link ModelDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModelOptionMapper.class, PricingClassMapper.class}, imports = {UUID.class})
public non-sealed interface ModelMapper extends EntityMapper<ModelDTO, Model> {

    @Mappings({
            @Mapping(expression = "java(UUID.fromString(modelDTO.getModelId()))", target = "id"),
            @Mapping(source = "modelType", target = "modelType"),
            @Mapping(source = "brand", target = "brand")
    })
    Model toEntity(ModelDTO modelDTO);

    @Mappings({
            @Mapping(expression = "java(model.getId().toString())", target = "modelId"),
            @Mapping(source = "modelType", target = "modelType"),
            @Mapping(source = "brand", target = "brand"),
            @Mapping(source = "model.modelOption", target = "modelOptionDTO"),
            @Mapping(source = "model.pricingClass", target = "princingDetailsDTO")
    })
    ModelDTO toDto(Model model);

    default Model fromId(UUID id) {
        if (id == null) {
            return null;
        }
        Model model = new Model();
        model.setId(id);
        return model;
    }
}

