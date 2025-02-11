
package hotel.com.service;

import hotel.com.dto.HotelSummaryDTO;
import hotel.com.dto.HotelDetailDTO;
import hotel.com.model.Hotel;

import java.util.List;
import java.util.Map;

public interface HotelService {
    List<HotelSummaryDTO> getAllHotels();
    HotelDetailDTO getHotelById(Long id);
    List<HotelSummaryDTO> searchHotels(String name, String brand, String city, String country, List<String> amenities);
    HotelSummaryDTO createHotel(Hotel hotel);
    void addAmenitiesToHotel(Long hotelId, List<String> amenities);
    Map<String, Long> getHotelHistogramByBrand();
    Map<String, Long> getHotelHistogramByCity();
    Map<String, Long> getHotelHistogramByCountry();
    Map<String, Long> getHotelHistogramByAmenities();

}
