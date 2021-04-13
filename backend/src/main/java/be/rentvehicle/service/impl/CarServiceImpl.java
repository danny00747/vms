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

import java.util.List;
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
        // List<Car> t = carDAO.findAllWithEagerRelationships();
        // t.forEach(System.out::println);
        return carDAO.findAllWithEagerRelationships()
                .stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }
}
