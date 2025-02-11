package hotel.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "amenities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Amenity(String name) {
        this.name = name;
    }
}
