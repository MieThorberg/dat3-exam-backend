package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rentals")
@NamedQuery(name = "Rental.deleteAllRows", query = "DELETE from Rental")
public class Rental implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "startDate")
    private String startDate;

    @Column(name = "endDate")
    private String endDate;

    @Column(name = "priceAnnual")
    private int priceAnnual;

    @Column(name = "deposit")
    private int deposit;

    @Column(name = "contactPerson")
    private String contactPerson;

    public Rental() {
    }

    public Rental(String startDate, String endDate, int priceAnnual, int deposit, String contactPerson) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceAnnual = priceAnnual;
        this.deposit = deposit;
        this.contactPerson = contactPerson;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPriceAnnual() {
        return priceAnnual;
    }

    public void setPriceAnnual(int priceAnnual) {
        this.priceAnnual = priceAnnual;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}