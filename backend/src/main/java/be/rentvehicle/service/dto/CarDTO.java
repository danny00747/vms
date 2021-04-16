package be.rentvehicle.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public @Data
class CarDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String carId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer madeInYear;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isDamaged = false;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer purchasedPrice;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String licensePlate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ModelDTO modelDTO;

    // private List<String> modelOptions;



    private String modelBrand;
    private Integer bagsNumber;
    private String modelType;


    public CarDTO(String carId, String licensePlate, String modelType, String modelBrand, Integer bagsNumber) {
        this.carId = carId;
        this.licensePlate = licensePlate;
        this.modelBrand = modelBrand;
        this.bagsNumber = bagsNumber;
        this.modelType = modelType;
    }
}
