package be.rentvehicle.service.impl;

import be.rentvehicle.dao.BookingDAO;
import be.rentvehicle.dao.RentDAO;
import be.rentvehicle.domain.Booking;
import be.rentvehicle.domain.Rent;
import be.rentvehicle.service.RentService;
import be.rentvehicle.service.dto.RentDTO;
import be.rentvehicle.service.impl.errors.ResourceNotFoundException;
import be.rentvehicle.service.mapper.RentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link RentService} interface.
 */
@Service
@Transactional
@Validated
public class RentServiceImpl implements RentService {

    private final RentMapper rentMapper;
    private final BookingDAO bookingDAO;
    private final RentDAO rentDAO;

    public RentServiceImpl(RentMapper rentMapper, BookingDAO bookingDAO, RentDAO rentDAO) {
        this.rentMapper = rentMapper;
        this.bookingDAO = bookingDAO;
        this.rentDAO = rentDAO;
    }

    @Override
    public RentDTO save(@Valid RentDTO rentDTO, String bookingId) {

        Booking booking = Optional.of(bookingDAO
                .findById(UUID.fromString(bookingId)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .orElseThrow(() -> new ResourceNotFoundException("No reservation was found with this id :" + bookingId));

        Rent rent = rentMapper.toEntity(rentDTO);
        rent.setBooking(booking);
        rent = rentDAO.save(rent);

        return rentMapper.toDto(rent);
    }

    @Override
    public List<RentDTO> findAll() {
        return rentDAO.findAll()
                .stream()
                .map(rentMapper::toDto)
                .collect(Collectors.toList());
    }
}
