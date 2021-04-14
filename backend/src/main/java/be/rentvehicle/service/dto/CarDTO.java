package be.rentvehicle.service.dto;

import be.rentvehicle.domain.ModelOption;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public @Data
class CarDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID carId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer madeInYear;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isDamaged = false;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer purchasedPrice;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String licensePlate;

    // private List<String> modelOptions;

    private String modelBrand;
    private Integer bagsNumber;
    private String modelType;


    public CarDTO(String licensePlate, String modelType, String modelBrand, Integer bagsNumber) {
        this.licensePlate = licensePlate;
        this.modelBrand = modelBrand;
        this.bagsNumber = bagsNumber;
        this.modelType = modelType;
    }
}
