package be.rentvehicle.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "id")
    private UUID id;

    private String road;

    @Column(name = "post_box")
    private int postBox;

    @Column(name = "house_number")
    private int houseNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "postcode")
    private Town town;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRoad() {
        return road;
    }

    public Address road(String road) {
        this.road = road;
        return this;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public int getPostBox() {
        return postBox;
    }

    public Address postBox(int postBox) {
        this.postBox = postBox;
        return this;
    }

    public void setPostBox(int postBox) {
        this.postBox = postBox;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Address houseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Address user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Town getTown() {
        return town;
    }

    public Address town(Town town) {
        this.town = town;
        return this;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
                "id=" + getId() +
                ", road='" + getRoad() + "'" +
                ", postBox=" + getPostBox() +
                ", houseNumber=" + getHouseNumber() +
                "}";
    }
}
