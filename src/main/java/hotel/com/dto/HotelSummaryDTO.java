package hotel.com.dto;

import hotel.com.model.Address;
import hotel.com.model.Contacts;
import hotel.com.model.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelSummaryDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;




    public static HotelSummaryDTO transferToShortInf(Hotel hotel) {
        HotelSummaryDTO hotelSummaryDTO = new HotelSummaryDTO();

        hotelSummaryDTO.setId(hotel.getId());
        hotelSummaryDTO.setName(hotel.getName());
        hotelSummaryDTO.setDescription(hotel.getDescription());
        hotelSummaryDTO.setAddressForm(hotel.getAddress());
        hotelSummaryDTO.setPhone(hotel.getContacts());

        return hotelSummaryDTO;
    }

    private void setAddressForm(Address address) {
        this.address =  address.getHouseNumber() +" "+ address.getStreet()+", " + address.getCity()+", "+ address.getPostCode()+", "+ address.getCountry();
    }

    private void setPhone(Contacts contacts) {
        this.phone = contacts.getPhone();
    }


}
/*Пример ответа:
        {
        "id": 1,
        "name": "DoubleTree by Hilton Minsk",
        "description": "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel's 20th floor ...",
        "address": "9 Pobediteley Avenue, Minsk, 220004, Belarus",
        "phone": "+375 17 309-80-00"
        }*/
