package be.rentvehicle.service.dto;

import be.rentvehicle.domain.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
public @Data class CarsDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String license_plate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String made_in_year;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<ModelDTO> models;


}
