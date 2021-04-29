package be.rentvehicle.service.impl;

import be.rentvehicle.dao.CarDAO;
import be.rentvehicle.domain.Car;
import be.rentvehicle.service.CarService;
import be.rentvehicle.service.dto.CarDTO;
import be.rentvehicle.service.mapper.CarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link CarService} interface.
 */
@Service
@Transactional
@Validated
public class CarServiceImpl implements CarService {

    private static final Logger log = LoggerFactory.getLogger(CarServiceImpl.class);

    private final CarDAO carDAO;
    private final CarMapper carMapper;

    public CarServiceImpl(CarDAO carDAO, CarMapper carMapper) {
        this.carDAO = carDAO;
        this.carMapper = carMapper;
    }

    @Override
    public List<CarDTO> findAll() {
        return carDAO.findAll()
                .stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarDTO> getAllByModelBrand(String brand) {
        log.debug("Request to get cars with this model brand : {}", brand);
        // carDAO.findAll();
        return carDAO.findAllByModelBrand(brand.substring(0, 1).toUpperCase() + brand.substring(1).toLowerCase())
                .stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CarDTO> partialUpdate(CarDTO carDTO) {
        log.debug("Request to partially update Car : {}", carDTO);
        return carDAO
                .findById(UUID.fromString(carDTO.getCarId()))
                .map(existingCar -> {
                            carMapper.partialUpdate(existingCar, carDTO);
                            return existingCar;
                        }
                )
                .map(carDAO::save)
                .map(carMapper::toDto);
    }

    @Override
    public Optional<CarDTO> getOneCarById(String id) {
        UUID carId;
        try {
            carId = UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Please provide a valid UUID");
        }

        return Optional.of(carDAO
                .findById(carId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(carMapper::toDto);
    }

    @Override
    public List<CarDTO> getBookedCars(Instant date) {

        if (date.compareTo(Instant.now().minus(3, ChronoUnit.MINUTES)) > 0) {

            List<Car> bookedCars = carDAO.findBetweens(date);

            if (bookedCars.size() == 0) {
                return carDAO.findAll()
                        .stream()
                        .map(carMapper::toDto)
                        .collect(Collectors.toList());
            } else {
                Set<UUID> carsToAvoid = bookedCars.stream()
                        .map(Car::getId)
                        .collect(Collectors.toSet());

                return carDAO.findAll()
                        .stream()
                        .filter(car -> !carsToAvoid.contains(car.getId()))
                        .map(carMapper::toDto)
                        .collect(Collectors.toList());
            }

        } else {
            return carDAO.findAll()
                    .stream()
                    .map(carMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<CarDTO> nativeQuery() {
        return carDAO.findAllWithEagerRelationships()
                .stream()
                .map(item -> new CarDTO(item[0].toString(), item[1].toString(), item[2].toString(), item[3].toString(), Integer.parseInt(item[4].toString()))
                ).collect(Collectors.toList());
        /*
        List<Map<String, Object>>
        return carDAO.findAllWithEagerRelationships()
                .stream()
                .map(item -> Map.of(
                        "licensePlate", item[0],
                        "modelType", item[1].toString(),
                        "modelBrand", item[2].toString(),
                        "bagsNumber", Integer.parseInt(item[3].toString()))
                ).collect(Collectors.toList());
         */
    }
}
