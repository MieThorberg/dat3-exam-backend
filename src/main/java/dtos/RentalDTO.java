package dtos;

import entities.Rental;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RentalDTO {

    private long id;
    private String startDate;
    private String endDate;
    private int priceAnnual;
    private int deposit;
    private String contactPerson;
    private HouseDTO house;

    public RentalDTO(Rental rental) {
        if(rental.getId() != 0)
            this.id = rental.getId();
        this.startDate = rental.getStartDate();
        this.endDate = rental.getEndDate();
        this.priceAnnual = rental.getPriceAnnual();
        this.deposit = rental.getDeposit();
        this.contactPerson = rental.getContactPerson();
        this.house = new HouseDTO(rental.getHouse());
    }

    public static Set<RentalDTO> getRentalDTOs(List<Rental> rentals) {
        Set<RentalDTO> rentalDTOS = new HashSet<>();
        rentals.forEach(rental -> rentalDTOS.add(new RentalDTO(rental)));
        return rentalDTOS;
    }

    public static Set<RentalDTO> getRentalDTOs(Set<Rental> rentals) {
        Set<RentalDTO> rentalDTOS = new HashSet<>();
        rentals.forEach(rental -> rentalDTOS.add(new RentalDTO(rental)));
        return rentalDTOS;
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

    public HouseDTO getHouse() {
        return house;
    }

    public void setHouse(HouseDTO house) {
        this.house = house;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalDTO rentalDTO = (RentalDTO) o;
        return id == rentalDTO.id && priceAnnual == rentalDTO.priceAnnual && deposit == rentalDTO.deposit && Objects.equals(startDate, rentalDTO.startDate) && Objects.equals(endDate, rentalDTO.endDate) && Objects.equals(contactPerson, rentalDTO.contactPerson) && Objects.equals(house, rentalDTO.house);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, priceAnnual, deposit, contactPerson, house);
    }
}
