package hotel.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;



    @Override
    public String toString() {
        return  houseNumber + " " + street + ", " + city+ ", " + postCode + ", " + country ;
    }


}
