package hotel.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "arrival_times")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArrivalTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String checkIn;
    private String checkOut;
}
