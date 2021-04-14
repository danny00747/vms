package be.rentvehicle.service.impl;

import be.rentvehicle.dao.CarDAO;
import be.rentvehicle.domain.Car;
import be.rentvehicle.service.CarService;
import be.rentvehicle.service.dto.CarDTO;
import be.rentvehicle.service.dto.CarsDTO;
import be.rentvehicle.service.mapper.CarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        // List<Map<String, String>> t = carDAO.findAllWithEagerRelationships();
        // t.forEach(System.out::println);
        return carDAO.findAll()
                .stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<CarDTO> finds() {
        return carDAO.findAllWithEagerRelationships()
                .stream()
                .map(item -> new CarDTO(item[0].toString(), item[1].toString(), item[2].toString(), Integer.parseInt(item[3].toString()))
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
