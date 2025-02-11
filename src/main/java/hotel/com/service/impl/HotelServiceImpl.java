package hotel.com.service.impl;

import hotel.com.controller.exception.HotelNotFoundException;
import hotel.com.dto.HotelSummaryDTO;
import hotel.com.dto.HotelDetailDTO;
import hotel.com.model.Amenity;
import hotel.com.model.Hotel;
import hotel.com.repository.*;
import hotel.com.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;
    private final AddressRepository addressRepository;
    private final ArrivalTimeRepository arrivalTimeRepository;
    private final ContactsRepository contactsRepository;

    @Override
    @Transactional
    public List<HotelSummaryDTO> getAllHotels() {


        return hotelRepository.findAll().stream()
                .map(HotelSummaryDTO::transferToShortInf)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HotelDetailDTO getHotelById(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel with ID " + hotelId + " not found"));

        return HotelDetailDTO.transferToDetailInf(hotel);
    }


    @Override
    @Transactional
    public List<HotelSummaryDTO> searchHotels(String name, String brand, String city, String country, List<String> amenities) {
        return hotelRepository.searchHotels(name, brand, city, country, amenities).stream().map(HotelSummaryDTO::transferToShortInf)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HotelSummaryDTO createHotel(Hotel hotel) {
        if (hotel.getAddress() != null && hotel.getAddress().getId() == null) {
            addressRepository.save(hotel.getAddress());
        }
        if (hotel.getContacts() != null && hotel.getContacts().getId() == null) {
            contactsRepository.save(hotel.getContacts());
        }
        if (hotel.getArrivalTime() != null && hotel.getArrivalTime().getId() == null) {
            arrivalTimeRepository.save(hotel.getArrivalTime());
        }
        Hotel savedHotel = hotelRepository.save(hotel);
        return HotelSummaryDTO.transferToShortInf(savedHotel);
    }


    @Override
    @Transactional
    public void addAmenitiesToHotel(Long hotelId, List<String> amenities) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel with ID " + hotelId + " not found"));

        for (String amenityName : amenities) {
            Amenity amenity = amenityRepository.findByName(amenityName)
                    .orElseGet(() -> amenityRepository.save(new Amenity(amenityName)));
            hotel.getAmenities().add(amenity);
        }

        hotelRepository.save(hotel);
    }


    @Override
    @Transactional
    public Map<String, Long> getHotelHistogramByCity() {
        List<Object[]> result = hotelRepository.getHotelHistogramByCity();
        return result.stream().collect(Collectors.toMap(row -> (String) row[0], row -> (Long) row[1]));
    }

    @Override
    public Map<String, Long> getHotelHistogramByCountry() {
        List<Object[]> result = hotelRepository.getHotelHistogramByCountry();
        return result.stream().collect(Collectors.toMap(row -> (String) row[0], row -> (Long) row[1]));
    }

    @Override
    @Transactional
    public Map<String, Long> getHotelHistogramByBrand() {
        List<Object[]> result = hotelRepository.getHotelHistogramByBrand();
        return result.stream().collect(Collectors.toMap(row -> (String) row[0], row -> (Long) row[1]));
    }

    @Override
    @Transactional
    public Map<String, Long> getHotelHistogramByAmenities() {
        List<Object[]> result = hotelRepository.getHotelHistogramByAmenities();
        return result.stream().collect(Collectors.toMap(row -> (String) row[0], row -> (Long) row[1]));
    }
}
