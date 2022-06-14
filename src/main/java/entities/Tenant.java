package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tenants")
@NamedQuery(name = "Tenant.deleteAllRows", query = "DELETE from Tenant")
public class Tenant implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "job")
    private String job;

    @ManyToMany (mappedBy = "tenants")
    Set<Rental> rentals = new HashSet<>();

    public Tenant() {
    }

    public Tenant(String name, String phone, String job) {
        this.name = name;
        this.phone = phone;
        this.job = job;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Set<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(Set<Rental> rentals) {
        this.rentals = rentals;
    }

   public void addRental(Rental rental) {
        this.rentals.add(rental);
    }

    public void removeRental(Rental rental) {
        this.rentals.remove(rental);
    }
}