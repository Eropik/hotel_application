package hotel.com.dto;

import hotel.com.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDetailDTO {
    private Long id;
    private String name;
    private String brand;
    private AddressDTO address;
    private ContactsDTO contacts;
    private ArrivalTimeDTO arrivalTime;
    private List<String> amenities;


    public static HotelDetailDTO transferToDetailInf(Hotel hotel) {
        HotelDetailDTO hotelDetailDTO = new HotelDetailDTO();

        hotelDetailDTO.setId(hotel.getId());
        hotelDetailDTO.setName(hotel.getName());
        hotelDetailDTO.setBrand(hotel.getBrand());
        hotelDetailDTO.setAddressForm(hotel.getAddress());
        hotelDetailDTO.setContactsForm(hotel.getContacts());
        hotelDetailDTO.setArrivalTimeForm(hotel.getArrivalTime());
        hotelDetailDTO.setAmenities(hotel.getAmenities());
        return hotelDetailDTO;
    }


    private void setAddressForm(Address address) {
        this.address = new AddressDTO(address);
    }

    private void setContactsForm(Contacts contacts) {
        this.contacts = new ContactsDTO(contacts);
    }

    private void setArrivalTimeForm(ArrivalTime arrivalTime) {
        this.arrivalTime = new ArrivalTimeDTO(arrivalTime);
    }

    private void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities.stream().map(Amenity::getName).collect(Collectors.toList());
        ;
    }


}
/*{
        "id": 1,
        "name": "DoubleTree by Hilton Minsk",
        "brand" "Hilton",
        "address":
        {
        "houseNumber": 9
        "street": "Pobediteley Avenue",
        "city": "Minsk",
        "county": "Belarus",
        "postCode": "220004"
        }
        "contacts":
        {
        "phone": "+375 17 309-80-00",
        "email": doubletreeminsk.info@hilton.com
        },
       "arrivalTime:
 {
                        "checkIn": "14:00",
                        "checkOut": "12:00"
                        },
      "amenities":
                        [
                        "Free parking",
                        "Free WiFi",
                        "Non-smoking rooms",
                        "Concierge",
                        "On-site restaurant",
                        "Fitness center",
                        "Pet-friendly rooms",
                        "Room service",
                        "Business center",
                        "Meeting rooms"
                        ]
         }*/
