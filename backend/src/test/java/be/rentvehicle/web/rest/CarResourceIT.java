package be.rentvehicle.web.rest;


import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import be.rentvehicle.VmsApplication;
import be.rentvehicle.domain.Car;
import be.rentvehicle.dao.CarDAO;
import be.rentvehicle.service.CarService;
import be.rentvehicle.service.dto.CarDTO;
import be.rentvehicle.service.mapper.CarMapper;

import java.util.UUID;
import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CarResource} REST controller.
 */
@SpringBootTest(classes = VmsApplication.class)
@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc
@WithMockUser
public class CarResourceIT {

    private static final UUID DEFAULT_CAR_ID = UUID.randomUUID();
    private static final UUID UPDATED_CAR_ID = UUID.fromString("5e21cb92-70ba-4886-b64d-2cf6fb593a1b");

    private static final Integer DEFAULT_CAR_NUMBER = 1;
    private static final Integer UPDATED_CAR_NUMBER = 2;

    private static final Integer DEFAULT_PURCHASE_PRICE = 14210;
    private static final Integer UPDATED_PURCHASE_PRICE = 14210;

    private static final String DEFAULT_LICENSE_PLATE = UUID.randomUUID().toString().substring(0, 9);
    private static final String UPDATED_LICENSE_PLATE = UUID.randomUUID().toString().substring(0, 9);

    private static final Integer DEFAULT_MADE_IN_YEAR = 2019;
    private static final Integer UPDATED_MADE_IN_YEAR = 2020;

    private static final Boolean DEFAULT_IS_DAMAGED = false;
    private static final Boolean UPDATED_IS_DAMAGED = false;

    private static final String ENTITY_API_URL = "/api/v1/cars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/cars";

    @Autowired
    private CarDAO carDAO;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarService carService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarMockMvc;

    private Car car;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Car createEntity(EntityManager em) {
        Car car = new Car();
        car.setId(DEFAULT_CAR_ID);
        car.setLicensePlate(DEFAULT_LICENSE_PLATE);
        car.setMadeInYear(DEFAULT_MADE_IN_YEAR);
        car.setIsDamaged(DEFAULT_IS_DAMAGED);
        car.setPurchasedPrice(DEFAULT_PURCHASE_PRICE);
        return car;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Car createUpdatedEntity(EntityManager em) {
        Car car = new Car();
        car.setId(UPDATED_CAR_ID);
        car.setLicensePlate(UPDATED_LICENSE_PLATE);
        car.setMadeInYear(UPDATED_MADE_IN_YEAR);
        car.setIsDamaged(UPDATED_IS_DAMAGED);
        car.setPurchasedPrice(UPDATED_PURCHASE_PRICE);
        return car;
    }

    @BeforeEach
    public void initTest() {
        car = createEntity(em);
    }

    @Test
    @Transactional
    @DisplayName("should get all cars")
    public void getAllCars() throws Exception {

        // Initialize the database
        carDAO.saveAndFlush(car);

        // Get all the carList
        restCarMockMvc.perform(get("/api/v1/cars"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].purchasedPrice").value(hasItem(DEFAULT_PURCHASE_PRICE)))
                .andExpect(jsonPath("$.[*].isDamaged").value(hasItem(DEFAULT_IS_DAMAGED)))
                .andExpect(jsonPath("$.[*].madeInYear").value(hasItem(DEFAULT_MADE_IN_YEAR)))
                .andExpect(jsonPath("$.[*].licensePlate").value(hasItem(DEFAULT_LICENSE_PLATE)));
    }

    @Test
    @Transactional
    @DisplayName("should get one car by its id")
    public void getCar() throws Exception {
        // Initialize the database
        Car savedCar = carDAO.saveAndFlush(car);

        // Get the car
        restCarMockMvc.perform(get("/api/v1/cars/{id}", savedCar.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.purchasedPrice").value(DEFAULT_PURCHASE_PRICE))
                .andExpect(jsonPath("$.isDamaged").value(DEFAULT_IS_DAMAGED))
                .andExpect(jsonPath("$.madeInYear").value(DEFAULT_MADE_IN_YEAR))
                .andExpect(jsonPath("$.licensePlate").value(DEFAULT_LICENSE_PLATE));
    }

    @Test
    @Transactional
    @DisplayName("should not find any car with a random id")
    public void getNonExistingCar() throws Exception {
        // Get the car
        restCarMockMvc.perform(get("/api/v1/cars/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("No car was found with this id")));
    }

    @Test
    @Transactional
    @DisplayName("should update a car given its id")
    public void updateCar() throws Exception {
        // Initialize the database
        Car savedCar = carDAO.saveAndFlush(car);

        // Create the Car
        CarDTO carDTO = carMapper.toDto(car);
        carDTO.setCarId(savedCar.getId().toString());
        carDTO.setMadeInYear(UPDATED_MADE_IN_YEAR);

        // Get updated car
        restCarMockMvc.perform(patch("/api/v1/cars/{id}", savedCar.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(carDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.madeInYear").value(UPDATED_MADE_IN_YEAR));

    }
}
