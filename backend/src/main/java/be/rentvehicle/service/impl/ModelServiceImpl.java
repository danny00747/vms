package be.rentvehicle.service.impl;

import be.rentvehicle.dao.ModelDAO;
import be.rentvehicle.service.ModelService;
import be.rentvehicle.service.dto.ModelDTO;
import be.rentvehicle.service.mapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link ModelService} interface.
 */
@Service
@Transactional
@Validated
public class ModelServiceImpl implements ModelService {

    private static final Logger log = LoggerFactory.getLogger(ModelServiceImpl.class);

    private final ModelDAO modelDAO;
    private final ModelMapper modelMapper;

    public ModelServiceImpl(ModelDAO modelDAO, ModelMapper modelMapper) {
        this.modelDAO = modelDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ModelDTO> findAll() {
        return modelDAO.findAll()
                .stream()
                .map(modelMapper::toDto)
                .collect(Collectors.toList());
    }
}
