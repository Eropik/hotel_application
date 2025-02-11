package hotel.com.dto;

import hotel.com.model.Address;
import lombok.Data;

@Data
public class AddressDTO {
    private int houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;

    public AddressDTO(Address address) {
        this.houseNumber = address.getHouseNumber();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.postCode = address.getPostCode();
    }

    @Override
    public String toString() {
        return  houseNumber + " " + street + ", " + city+ ", " + postCode + ", " + country ;
    }
}
