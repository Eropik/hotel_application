
package hotel.com.repository;

import hotel.com.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {






    @Query("SELECT h FROM Hotel h " +
            "JOIN h.address a " +
            "LEFT JOIN h.amenities am " +
            "WHERE  h.name LIKE %:name% " +
            "OR h.brand LIKE %:brand% " +
            "OR  a.city LIKE %:city% " +
            "OR  a.country LIKE %:country% " +
            "OR  am.name IN :amenities")
    List<Hotel> searchHotels(String name, String brand, String city, String country, List<String> amenities);


    @Query("SELECT h.brand, COUNT(h) FROM Hotel h GROUP BY h.brand")
    List<Object[]> getHotelHistogramByBrand();

    @Query("SELECT a.city, COUNT(h) FROM Hotel h JOIN h.address a GROUP BY a.city")
    List<Object[]> getHotelHistogramByCity();

    @Query("SELECT a.country, COUNT(h) FROM Hotel h JOIN h.address a GROUP BY a.country ")
    List<Object[]> getHotelHistogramByCountry();

    @Query("SELECT am.name, COUNT(h) FROM Hotel h JOIN h.amenities am GROUP BY am.name")
    List<Object[]> getHotelHistogramByAmenities();
}

