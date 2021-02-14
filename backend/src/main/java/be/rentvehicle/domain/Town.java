package be.rentvehicle.domain;
import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Town.
 */
@Entity
@Table(name = "town")
public class Town implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "postcode")
    @NotNull(message = "postcode is a required field.")
    @Min(value = 1, message = "postcode must be greater than or equal to 1")
    private Integer postcode;

    @NotNull(message = "A town's name is a required field.")
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "town")
    private Set<Address> addresses = new HashSet<>();

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public String getName() {
        return name;
    }

    public Town name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public Town addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Town addAddresses(Address address) {
        this.addresses.add(address);
        address.setTown(this);
        return this;
    }

    public Town removeAddresses(Address address) {
        this.addresses.remove(address);
        address.setTown(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Town{" +
                ", postcode='" + getPostcode() + "'" +
                ", name='" + getName() + "'" +
                "}";
    }
}
