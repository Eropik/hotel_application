package hotel.com.dto;

import hotel.com.model.Contacts;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ContactsDTO {
    private String phone;
    private String email;

    public ContactsDTO(Contacts contacts) {
        this.phone = contacts.getPhone();
        this.email = contacts.getEmail();
    }

}
