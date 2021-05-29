package be.rentvehicle.service.mapper;

import be.rentvehicle.domain.Booking;
import be.rentvehicle.service.dto.BookingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.UUID;

/**
 * Mapper for the entity {@link Booking} and its DTO {@link BookingDTO}.
 */
@Mapper(componentModel = "spring", uses = {CarMapper.class, RentMapper.class}, imports = {UUID.class})
public non-sealed interface BookingMapper extends EntityMapper<BookingDTO, Booking> {

    @Mappings({
            @Mapping(expression = "java(booking.getId().toString())", target = "bookingId"),
            @Mapping(source = "booking.car", target = "carDTO"),
            @Mapping(source = "booking.rent", target = "rentDTO")
    })
    BookingDTO toDto(Booking booking);

    @Mappings({
            @Mapping(target = "id", ignore = true),
    })
    Booking toEntity(BookingDTO bookingDTO);
}
