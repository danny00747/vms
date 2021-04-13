package be.rentvehicle.domain;

import be.rentvehicle.config.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * A ModelOption entity.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "models_options")
public @Data class ModelOption extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "option_code")
    private String option_code;

    @Max(10)
    @Column(nullable = false)
    private Integer seats_number;

    @NotNull
    @Pattern(regexp = Constants.EMAIL_REGEX)
    @Column(nullable = false)
    private Boolean has_air_Conditioner;

    @NotNull
    @Column(nullable = false)
    private Boolean is_automatic;

    @Max(10)
    private Integer bags_number;
}


