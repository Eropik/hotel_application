package hotel.com.dto;

import hotel.com.model.Amenity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class AmenityDTO {


        private String name;

        public AmenityDTO(Amenity amenity) {
            this.name = amenity.getName();
        }
}
