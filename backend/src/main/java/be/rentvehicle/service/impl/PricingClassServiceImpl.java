package be.rentvehicle.service.impl;

import be.rentvehicle.dao.PricingClassDAO;
import be.rentvehicle.domain.enumeration.PRICINGCLASS;
import be.rentvehicle.service.PricingClassService;
import be.rentvehicle.service.dto.PricingClassDTO;
import be.rentvehicle.service.mapper.PricingClassMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link PricingClassService} interface.
 */
@Service
@Transactional
@Validated
public class PricingClassServiceImpl implements PricingClassService {

    private static final Logger log = LoggerFactory.getLogger(PricingClassServiceImpl.class);
    private final PricingClassMapper pricingClassMapper;
    private final PricingClassDAO pricingClassDAO;

    public PricingClassServiceImpl(PricingClassMapper pricingClassMapper, PricingClassDAO pricingClassDAO) {
        this.pricingClassMapper = pricingClassMapper;
        this.pricingClassDAO = pricingClassDAO;
    }

    @Override
    public List<PricingClassDTO> getAll() {
        return pricingClassDAO.findAll()
                .stream()
                .map(pricingClassMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PricingClassDTO> updatePricingClass(String className, PricingClassDTO pricingClassDTO) {
        log.debug("Request to partially update PricingClass : {}", pricingClassDTO);
        return pricingClassDAO
                .findById(getPricingClassEnum(className))
                .map(existingClass -> {
                            pricingClassMapper.partialUpdate(existingClass, pricingClassDTO);
                            return existingClass;
                        }
                )
                .map(pricingClassDAO::save)
                .map(pricingClassMapper::toDto);
    }

    private PRICINGCLASS getPricingClassEnum(String className) {
        if (className.equals("CLASS_A")) {
            return PRICINGCLASS.CLASS_A;
        } else if (className.equals("CLASS_B")) {
            return PRICINGCLASS.CLASS_B;
        } else {
            return PRICINGCLASS.CLASS_C;
        }
    }
}
