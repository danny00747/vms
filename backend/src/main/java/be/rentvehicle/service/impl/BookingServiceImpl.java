package be.rentvehicle.service.impl;

import be.rentvehicle.dao.BookingDAO;
import be.rentvehicle.dao.CarDAO;
import be.rentvehicle.dao.UserDAO;
import be.rentvehicle.domain.Booking;
import be.rentvehicle.domain.Car;
import be.rentvehicle.domain.User;
import be.rentvehicle.domain.enumeration.BOOKINGSTATE;
import be.rentvehicle.security.SecurityUtils;
import be.rentvehicle.service.BookingService;
import be.rentvehicle.service.CarService;
import be.rentvehicle.service.MailService;
import be.rentvehicle.service.UserService;
import be.rentvehicle.service.dto.BookingDTO;
import be.rentvehicle.service.dto.CarDTO;
import be.rentvehicle.service.impl.errors.ResourceFoundException;
import be.rentvehicle.service.impl.errors.UserNotFoundException;
import be.rentvehicle.service.mapper.BookingMapper;
import be.rentvehicle.service.mapper.RentMapper;
import be.rentvehicle.service.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link BookingService} interface.
 */
@Service
@Transactional
@Validated
public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDAO;
    private final CarDAO carDAO;
    private final UserDAO userDAO;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final CarService carService;
    private final RentMapper rentMapper;
    private final MailService mailService;
    private final UserInfoMapper userInfoMapper;

    public BookingServiceImpl(BookingDAO bookingDAO, CarDAO carDAO, UserDAO userDAO, BookingMapper bookingMapper,
                              UserService userService, CarService carService, RentMapper rentMapper, MailService mailService,
                              UserInfoMapper userInfoMapper) {
        this.bookingDAO = bookingDAO;
        this.carDAO = carDAO;
        this.userDAO = userDAO;
        this.bookingMapper = bookingMapper;
        this.userService = userService;
        this.carService = carService;
        this.rentMapper = rentMapper;
        this.mailService = mailService;
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public List<BookingDTO> findAll() {

        return bookingDAO.findAll()
                .stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO save(@Valid BookingDTO bookingDTO, String cardId) {

        Booking booking = bookingMapper.toEntity(bookingDTO);

        booking.setBookingState(BOOKINGSTATE.OPEN);

        User user = Optional.of(SecurityUtils
                .getCurrentAuthenticatedUser()
                .flatMap(userDAO::findOneByUsername))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .orElseThrow(() -> new UserNotFoundException("User could not be found"));

        Car car = Optional.of(carDAO
                .findById(validatedId(cardId)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .orElseThrow(() -> new ResourceFoundException("No car was found with this id :" + cardId));

        booking.setUser(user);
        booking.setCar(car);
        booking = bookingDAO.save(booking);

        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(Locale.ENGLISH)
                .withZone(ZoneId.systemDefault());

            /*
                    long nbOfDaysBetween = ChronoUnit.DAYS.between(booking.getWithdrawalDate(), booking.getReturnDate());
            long total = booking.getCar().getModel().getPricingClass().getCostPerDay() * nbOfDaysBetween;

                    mailService.sendReservationEMail(
                    "danbarca955@gmail.com",
                    booking.getId().toString(),
                    booking.getCar().getModel().getBrand() + " " + booking.getCar().getModel().getModelType(),
                    formatter.format(booking.getReturnDate()),
                    formatter.format(booking.getWithdrawalDate()),
                    booking.getCar().getModel().getPricingClass().getCostPerDay().toString() + "€",
                    total + "€");
             */

        return bookingMapper.toDto(booking);
    }

    @Override
    public Optional<String> deleteBooking(String bookingId) {
        return Optional.of(bookingDAO
                .findById(UUID.fromString(bookingId)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(booking -> {
                    bookingDAO.delete(booking);
                    return booking.getUser().getUsername() + "'s reservation has been successfully deleted !";
                });
    }

    @Override
    public Optional<String> cancelReservation(String id) {
        return Optional.of(bookingDAO
                .findById(validatedId(id)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(booking -> {
                    if (!booking.getBookingState().toString().equals(BOOKINGSTATE.CANCELLED.toString())) {
                        booking.setBookingState(BOOKINGSTATE.CANCELLED);
                        booking.setCancellationDate(Instant.now());
                        booking.setCar(null);
                        bookingDAO.save(booking);
                        return "This reservation has been successfully cancelled !";
                    }
                    return "This reservation has already been cancelled.";
                });
    }

    private UUID validatedId(String id) {
        UUID validId;
        try {
            validId = UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Please provide a valid UUID");
        }
        return validId;
    }
}

// bookingDAO.findAllByWithdrawalDateLessThanAndReturnDateGreaterThan(Instant.parse("2021-06-06T23:00:00.000Z"), Instant.parse("2021-06-09T23:00:00.000Z"))
