package hotel.com.dto;

import hotel.com.model.ArrivalTime;
import lombok.Data;

@Data
public class ArrivalTimeDTO {

    private String checkIn;
    private String checkOut;

    public ArrivalTimeDTO(ArrivalTime arrivalTime){
        this.checkIn = arrivalTime.getCheckIn();
        this.checkOut = arrivalTime.getCheckOut();
    }
}
