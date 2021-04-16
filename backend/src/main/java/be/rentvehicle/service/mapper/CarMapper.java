package be.rentvehicle.service.mapper;

import be.rentvehicle.domain.Car;
import be.rentvehicle.service.dto.CarDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.UUID;

/**
 * Mapper for the entity {@link Car} and its DTO {@link CarDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModelMapper.class})
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
            @Mapping(expression = "java(car.getId().toString())", target = "carId"),
            @Mapping(source = "madeInYear", target = "madeInYear"),
            @Mapping(source = "purchasedPrice", target = "purchasedPrice"),
            @Mapping(source = "licensePlate", target = "licensePlate"),
            @Mapping(source = "model", target = "modelDTO"),
            @Mapping(source = "isDamaged", target = "isDamaged")
    })
    CarDTO toDto(Car car);

    default UUID setCarId(String id){
        return UUID.fromString(id);
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
