package be.rentvehicle.service.impl;

import be.rentvehicle.dao.BookingDAO;
import be.rentvehicle.service.BookingService;
import be.rentvehicle.service.UserService;
import be.rentvehicle.service.dto.BookingDTO;
import be.rentvehicle.service.dto.UserInfoDTO;
import be.rentvehicle.service.mapper.BookingMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link BookingService} interface.
 */
@Service
@Transactional
@Validated
public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDAO;
    private final BookingMapper bookingMapper;

    public BookingServiceImpl(BookingDAO bookingDAO, BookingMapper bookingMapper) {
        this.bookingDAO = bookingDAO;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public List<BookingDTO> findAll() {
        return bookingDAO.findAll()
                .stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }
}

// bookingDAO.findAllByWithdrawalDateLessThanAndReturnDateGreaterThan(Instant.parse("2021-06-06T23:00:00.000Z"), Instant.parse("2021-06-09T23:00:00.000Z"))
