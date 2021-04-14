package be.rentvehicle.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="models",  uniqueConstraints={
        @UniqueConstraint(columnNames={"model_type", "brand"})
})
public @Data class Model extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "id")
    private UUID id;

    @Column(name = "model_type", nullable = false)
    @NotNull(message = "modelType is a required field.")
    private String modelType;

    @Column(name = "brand", length = 128, nullable = false)
    @NotNull(message = "brand is a required field.")
    private String brand;

    @OneToMany(mappedBy = "model")
    private Set<Car> cars = new HashSet<>();


      @ManyToMany
    @JoinTable(
            name = "cars_models_options",
            joinColumns = {@JoinColumn(name = "model_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "option_code", referencedColumnName = "option_code")})
    private Set<ModelOption> modelOptions = new HashSet<>();


}
