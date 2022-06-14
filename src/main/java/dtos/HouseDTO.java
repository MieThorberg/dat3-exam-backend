package dtos;

import entities.House;
import entities.Rental;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class HouseDTO {
    private long id;
    private String address;
    private String city;
    private int numberOfRooms;

    public HouseDTO(House house) {
        if(house.getId() != 0)
            this.id = house.getId();
        this.address = house.getAddress();
        this.city = house.getCity();
        this.numberOfRooms = house.getNumberOfRooms();
    }

    public static Set<HouseDTO> getHouseDTOs(List<House> houses) {
        Set<HouseDTO> houseDTOS = new HashSet<>();
        houses.forEach(house -> houseDTOS.add(new HouseDTO(house)));
        return houseDTOS;
    }

    public static Set<HouseDTO> getHouseDTOs(Set<House> houses) {
        Set<HouseDTO> houseDTOS = new HashSet<>();
        houses.forEach(house -> houseDTOS.add(new HouseDTO(house)));
        return houseDTOS;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseDTO houseDTO = (HouseDTO) o;
        return id == houseDTO.id && numberOfRooms == houseDTO.numberOfRooms && Objects.equals(address, houseDTO.address) && Objects.equals(city, houseDTO.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, city, numberOfRooms);
    }
}
