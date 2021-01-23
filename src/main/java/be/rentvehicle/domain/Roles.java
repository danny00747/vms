package be.rentvehicle.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "roles")
public class Roles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> user) {
        this.users = user;
    }

    public void setUser(User user) {
        this.users.add(user);
    }

    public Roles user(List<User> user) {
        this.users = user;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Authority{" +
                "name='" + name + '\'' +
                "}";
    }
}
