package be.rentvehicle.service.mapper;

import be.rentvehicle.domain.Car;
import be.rentvehicle.domain.Model;
import be.rentvehicle.domain.ModelOption;
import be.rentvehicle.domain.Roles;
import be.rentvehicle.service.dto.CarDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link Car} and its DTO {@link CarDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModelMapper.class, ModelOptionMapper.class})
public non-sealed interface CarMapper extends EntityMapper<CarDTO, Car> {

    @Mappings({
            @Mapping(source = "carId", target = "id"),
            @Mapping(source = "madeInYear", target = "madeInYear"),
            @Mapping(source = "purchasedPrice", target = "purchasedPrice"),
            @Mapping(source = "licensePlate", target = "licensePlate"),
            @Mapping(source = "isDamaged", target = "isDamaged")
    })
    Car toEntity(CarDTO carDTO);

    @Mappings({
            @Mapping(source = "id", target = "carId"),
            @Mapping(source = "madeInYear", target = "madeInYear"),
            @Mapping(source = "purchasedPrice", target = "purchasedPrice"),
            @Mapping(source = "licensePlate", target = "licensePlate"),
            @Mapping(source = "model.brand", target = "modelBrand"),
            @Mapping(source = "model.modelType", target = "modelType"),
            @Mapping(source = "isDamaged", target = "isDamaged")
    })
    CarDTO toDto(Car car);

    default Set<ModelOption> mapAddress(Set<ModelOption> relation){
        //create new arraylist
        // create new AddressObject and set completeAddress to address.city
        // add that to list and return list

        Set<ModelOption> roles = new HashSet<>();

        if (relation != null) {
            roles = relation.stream()
                    .map(string -> {
                        ModelOption auth = new ModelOption();
                        auth.setBagsNumber(string.getBagsNumber());
                        auth.setOptionCode(string.getOptionCode());
                        auth.setIsAutomatic(string.getIsAutomatic());
                        auth.setHasAirConditioner(string.getHasAirConditioner());
                        return auth;
                    }).collect(Collectors.toSet());
        }
        return roles;

    }

    default Car fromId(UUID id) {
        if (id == null) {
            return null;
        }
        Car car = new Car();
        car.setId(id);
        return car;
    }
}
