package hotel.com.controller;

import hotel.com.dto.HotelDetailDTO;
import hotel.com.dto.HotelSummaryDTO;
import hotel.com.model.Hotel;
import hotel.com.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelControllerTest {

    @Mock
    private HotelService hotelService;

    @InjectMocks
    private HotelController hotelController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetAllHotels_thenReturnListOfHotelSummaryDTOs() {
        List<HotelSummaryDTO> mockHotels = Arrays.asList(
                new HotelSummaryDTO(1L, "Hotel A", "Description A", "Address A", "12345"),
                new HotelSummaryDTO(2L, "Hotel B", "Description B", "Address B", "67890")
        );

        when(hotelService.getAllHotels()).thenReturn(mockHotels);

        ResponseEntity<List<HotelSummaryDTO>> response = hotelController.getAllHotels();

        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        verify(hotelService, times(1)).getAllHotels();
    }

    @Test
    void whenGetHotelById_thenReturnHotelDetailDTO() {
        HotelDetailDTO mockHotel = new HotelDetailDTO(1L, "Hotel A", "Brand A", null, null, null, List.of("WiFi", "Parking"));

        when(hotelService.getHotelById(1L)).thenReturn(mockHotel);

        ResponseEntity<HotelDetailDTO> response = hotelController.getHotelById(1L);

        assertNotNull(response);
        assertEquals("Hotel A", response.getBody().getName());
        assertEquals(1L, response.getBody().getId());
        verify(hotelService, times(1)).getHotelById(1L);
    }

    @Test
    void whenCreateHotel_thenReturnCreatedHotelSummaryDTO() {
        Hotel mockHotel = new Hotel();
        mockHotel.setName("New Hotel");
        HotelSummaryDTO mockResponse = new HotelSummaryDTO(1L, "New Hotel", "Description", "Address", "12345");

        when(hotelService.createHotel(mockHotel)).thenReturn(mockResponse);

        ResponseEntity<HotelSummaryDTO> response = hotelController.createHotel(mockHotel);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("New Hotel", response.getBody().getName());
        verify(hotelService, times(1)).createHotel(mockHotel);
    }

    @Test
    void whenAddAmenitiesToHotel_thenReturnNoContent() {
        doNothing().when(hotelService).addAmenitiesToHotel(1L, List.of("WiFi", "Gym"));

        ResponseEntity<Void> response = hotelController.addAmenitiesToHotel(1L, List.of("WiFi", "Gym"));

        assertEquals(204, response.getStatusCodeValue());
        verify(hotelService, times(1)).addAmenitiesToHotel(1L, List.of("WiFi", "Gym"));
    }

    @Test
    void whenSearchHotels_thenReturnListOfHotelSummaryDTOs() {
        List<HotelSummaryDTO> mockHotels = Arrays.asList(
                new HotelSummaryDTO(1L, "Hotel A", "Description A", "Address A", "12345"),
                new HotelSummaryDTO(2L, "Hotel B", "Description B", "Address B", "67890")
        );

        when(hotelService.searchHotels("Hotel", null, null, null, null)).thenReturn(mockHotels);

        ResponseEntity<List<HotelSummaryDTO>> response = hotelController.searchHotels("Hotel", null, null, null, null);

        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        verify(hotelService, times(1)).searchHotels("Hotel", null, null, null, null);
    }

    @Test
    void whenGetHotelHistogramByCity_thenReturnHistogramData() {
        Map<String, Long> mockHistogram = Map.of("New York", 5L, "Los Angeles", 3L);

        when(hotelService.getHotelHistogramByCity()).thenReturn(mockHistogram);

        ResponseEntity<Map<String, Long>> response = hotelController.getHotelHistogram("city");

        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        verify(hotelService, times(1)).getHotelHistogramByCity();
    }

    @Test
    void whenGetHotelHistogramWithInvalidParam_thenReturnBadRequest() {
        ResponseEntity<Map<String, Long>> response = hotelController.getHotelHistogram("invalidParam");

        assertEquals(400, response.getStatusCodeValue());
    }
}
