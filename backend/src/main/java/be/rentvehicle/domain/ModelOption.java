package be.rentvehicle.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * A ModelOption entity.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "models_options")
public @Data class ModelOption extends AbstractAuditingEntity {

    @Id
    @Column(name = "option_code", columnDefinition = "option_code")
    private String optionCode;

    @Column(name = "seats_number", nullable = false)
    private Integer seatsNumber;

    @Column(name = "has_air_conditioner", nullable = false)
    private Boolean hasAirConditioner;

    @Column(name = "is_automatic", nullable = false)
    private Boolean isAutomatic;

    @Column(name = "bags_number", nullable = false)
    private Integer bagsNumber;

     @ManyToMany(mappedBy = "modelOptions")
     private Set<Model> models;
}


