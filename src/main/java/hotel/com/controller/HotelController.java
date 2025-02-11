package hotel.com.controller;

import hotel.com.dto.HotelDetailDTO;
import hotel.com.dto.HotelSummaryDTO;
import hotel.com.model.Hotel;
import hotel.com.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @Operation(summary = "Get all hotels")
    @ApiResponse(responseCode = "200", description = "List of all hotels returned successfully",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = HotelSummaryDTO.class))})
    @GetMapping("/hotels")
    public ResponseEntity<List<HotelSummaryDTO>> getAllHotels() {
        List<HotelSummaryDTO> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }


    @Operation(summary = "Get hotel by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the hotel",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HotelDetailDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Hotel not found", content = @Content)
    })
    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelDetailDTO> getHotelById(@PathVariable Long id) {
        try {
            HotelDetailDTO hotel = hotelService.getHotelById(id);
            return ResponseEntity.ok(hotel);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @Operation(summary = "Create a new hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hotel created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HotelSummaryDTO.class))})
    })
    @PostMapping("/hotels")
    public ResponseEntity<HotelSummaryDTO> createHotel(@RequestBody Hotel hotel) {
        HotelSummaryDTO createdHotel = hotelService.createHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
    }


    @Operation(summary = "Add amenities to a hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Amenities added successfully"),
            @ApiResponse(responseCode = "404", description = "Hotel not found", content = @Content)
    })
    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<Void> addAmenitiesToHotel(@PathVariable Long id, @RequestBody List<String> amenities) {
        try {
            hotelService.addAmenitiesToHotel(id, amenities);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @Operation(summary = "Search hotels by parameters")
    @ApiResponse(responseCode = "200", description = "List of hotels that match search criteria",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = HotelSummaryDTO.class))})
    @GetMapping("/search")
    public ResponseEntity<List<HotelSummaryDTO>> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) List<String> amenities) {
        List<HotelSummaryDTO> hotels = hotelService.searchHotels(name, brand, city, country, amenities);
        return ResponseEntity.ok(hotels);
    }


    @Operation(summary = "Get hotel histogram by parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histogram data returned successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(type = "object"))}),
            @ApiResponse(responseCode = "400", description = "Invalid histogram parameter", content = @Content)
    })
    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Long>> getHotelHistogram(@PathVariable String param) {
        try {
            Map<String, Long> histogram = switch (param) {
                case "brand" -> hotelService.getHotelHistogramByBrand();
                case "city" -> hotelService.getHotelHistogramByCity();
                case "country" -> hotelService.getHotelHistogramByCountry();
                case "amenities" -> hotelService.getHotelHistogramByAmenities();
                default -> throw new IllegalArgumentException("Unknown parameter: " + param);
            };
            return ResponseEntity.ok(histogram);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}