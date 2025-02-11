package hotel.com.service.impl;

import hotel.com.controller.exception.HotelNotFoundException;
import hotel.com.dto.HotelDetailDTO;
import hotel.com.dto.HotelSummaryDTO;
import hotel.com.model.*;
import hotel.com.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelServiceImplTest {

    private static Hotel hotel = new Hotel(1L, "Hotel A", "Description A", "Brand A", new Address(1l,1,"Street A","City A","Country A", "Postcode A"), new Contacts(1L,"phone A","email A"), new ArrivalTime(1L,"A","B"), new ArrayList<>());



    @InjectMocks
    private HotelServiceImpl hotelService;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private AmenityRepository amenityRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ArrivalTimeRepository arrivalTimeRepository;

    @Mock
    private ContactsRepository contactsRepository;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetAllHotels_thenReturnHotelSummaryDTOList() {
        List<Hotel> hotels = List.of(hotel);
        when(hotelRepository.findAll()).thenReturn(hotels);

        List<HotelSummaryDTO> result = hotelService.getAllHotels();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Hotel A", result.get(0).getName());
    }

    @Test
    void whenGetHotelById_thenReturnHotelDetailDTO() {
         when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        HotelDetailDTO result = hotelService.getHotelById(1L);

        assertNotNull(result);
        assertEquals("Hotel A", result.getName());
        assertEquals("Brand A", result.getBrand());
    }

    @Test
    void whenGetHotelById_thenThrowHotelNotFoundException() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HotelNotFoundException.class, () -> hotelService.getHotelById(1L));
    }

    @Test
    void whenCreateHotel_thenReturnCreatedHotelSummaryDTO() {
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        HotelSummaryDTO result = hotelService.createHotel(hotel);

        assertNotNull(result);
        assertEquals("Hotel A", result.getName());
        assertEquals("Description A", result.getDescription());
    }

    @Test
    void whenAddAmenitiesToHotel_thenSaveUpdatedHotel() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(amenityRepository.findByName("Free WiFi")).thenReturn(Optional.empty());
        when(amenityRepository.save(any(Amenity.class))).thenReturn(new Amenity("Free WiFi"));

        hotelService.addAmenitiesToHotel(1L, List.of("Free WiFi"));

        verify(hotelRepository).save(hotel);
        assertEquals(1, hotel.getAmenities().size());
        assertEquals("Free WiFi", hotel.getAmenities().get(0).getName());
    }

    @Test
    void whenAddAmenitiesToHotel_thenThrowHotelNotFoundException() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HotelNotFoundException.class, () -> hotelService.addAmenitiesToHotel(1L, List.of("Free WiFi")));
    }



    @Test
    void whenGetHotelHistogramByCity_thenReturnValidData() {
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[]{"City A", 3L});
        when(hotelRepository.getHotelHistogramByCity()).thenReturn(data);

        Map<String, Long> result = hotelService.getHotelHistogramByCity();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(3L, result.get("City A"));
    }



}
