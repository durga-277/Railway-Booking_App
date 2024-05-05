package org.mohan.com.railwaybookingapp.controller;


import org.mohan.com.railwaybookingapp.model.*;
import org.mohan.com.railwaybookingapp.service.UserBookingService;
import org.mohan.com.railwaybookingapp.service.impl.UserBookingImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping ("/api/user/trains")
@CrossOrigin(origins = "http://localhost:3000")
public class UserBookingController {

    private final UserBookingService userBookingService;


    public UserBookingController(UserBookingService userBookingService) {
        this.userBookingService = userBookingService;
    }
    @GetMapping
    public ResponseEntity<List<Train>> getALlTrains(){
        return ResponseEntity.ok(userBookingService.getAllAvailableTrains());
    }

    @GetMapping("/station/{source}/{destination}")
    public ResponseEntity<List<Train>> getAllByStation(@PathVariable("source") String source,@PathVariable("destination") String destination){
        List<Train> train = userBookingService.getTrainDetailsByStation(source,destination);
        if (train.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userBookingService.getTrainDetailsByStation(source,destination));
    }

    @GetMapping("/trainId/{id}")
    public ResponseEntity<?> getAllTrainsByTrainId(@PathVariable String id){
       Train train =userBookingService.getAllTrainsByTrainId(id);

       if(train!=null){
           return ResponseEntity.ok(train);
       }
       return ResponseEntity.badRequest().body("Train Id not found");
    }
    @GetMapping("/{username}/bookings")  // give username then get the booking on him
    public List<Booking> getAllBookingByUserName(@PathVariable String username){
        return userBookingService.getAllBookingsByUserName(username);
    }
    @GetMapping("/booking/{PNR}")
    public List<Booking> getDetailsByPNR(@PathVariable String PNR){
        return userBookingService.getDetailsByPNR(PNR);
    }
//    @PostMapping("/{trainId}/bookings")
//    public  ResponseEntity<String> addBookingDetails(@PathVariable String trainId, @RequestBody BookingRequest bookingRequest){
//           if(userBookingService.addBookingForTrain(trainId,bookingRequest)){
//               return new ResponseEntity<>("Seat Registered successfully",HttpStatus.CREATED);
//           }
//           return new ResponseEntity<>("Seat Not Available",HttpStatus.BAD_REQUEST);
//    }

//    @PostMapping("/{trainId}/bookings")
//    public  ResponseEntity<String> addBookingDetails(@PathVariable String trainId, @RequestBody List<BookingRequest> bookingRequest){
//      for(BookingRequest bookingRequest1 : bookingRequest) {
//          if (!userBookingService.addBookingForTrain(trainId, bookingRequest1)) {
//              return new ResponseEntity<>("Seat Not Available",HttpStatus.BAD_REQUEST);
//
//          }
//      }
//        return new ResponseEntity<>("Seat Registered successfully", HttpStatus.CREATED);
//    }
    //this above is working


    // %%%%%%%%%%
//    @PostMapping("/{trainId}/bookings")
//    public ResponseEntity<?> addBookingDetails(@PathVariable String trainId, @RequestBody List<BookingRequest> bookingRequest) {
//        List<BookingResponse> bookingResponses = new ArrayList<>();
//
//        for (BookingRequest bookingRequest1 : bookingRequest) {
//
//            ResponseEntity<String> responseEntity = userBookingService.addBookingForTrain(trainId, bookingRequest1);
//            if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
//                // If booking was not successful, return the error message
//                return responseEntity;
//            }
//            // Extract booking details from the response body
//            String responseBody = responseEntity.getBody();
//            String[] parts = responseBody.split(":");
////            String[] parts = responseEntity.getBody().split(":");
//            String bookingId = parts[0].trim();
//            String pnrNumber = parts[1].trim();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//            LocalDateTime bookingDateTime = LocalDateTime.parse(parts[2].trim(),formatter);
//            String name = parts[3].trim(); // Extract name
//            int age = Integer.parseInt(parts[4].trim()); // Extract age
//            String username = parts[5].trim(); // Extract username
//            String seatNumber = parts[6].trim(); // Extract seat number
//            double price = Double.parseDouble(parts[7].trim()); // Extract price
//            String seatSection = parts[8].trim(); // Extract seat section
//
//            // Populate booking response with booking details
//
//            BookingResponse bookingResponse = new BookingResponse();
//            bookingResponse.setBookingId(bookingId);
//            bookingResponse.setPNR(pnrNumber);
//            bookingResponse.setBookingDateTime(bookingDateTime);
//            bookingResponse.setName(name); // Set name
//            bookingResponse.setAge(age); // Set age
//            bookingResponse.setUserName(username); // Set username
//            bookingResponse.setSeatNumber(seatNumber); // Set seat number
//            bookingResponse.setPrice(price); // Set price
//            bookingResponse.setSeatSection(seatSection); // Set seat section
//
//            // Add more details as needed
//
//            bookingResponses.add(bookingResponse);
//        }
//        // If all bookings are successful, return success message along with booking details
//        return ResponseEntity.ok().body(bookingResponses);
//    }

    @SuppressWarnings("unchecked")
    @PostMapping("/{trainId}/bookings")
    public ResponseEntity<?> addBookingDetails(@PathVariable String trainId, @RequestBody List<BookingRequest> bookingRequest) {
        List<Map<String, String>> bookingResponses = new ArrayList<>();

        for (BookingRequest bookingRequest1 : bookingRequest) {
            ResponseEntity<?> responseEntity = userBookingService.addBookingForTrain(trainId, bookingRequest1);
            if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
                // If booking was not successful, return the error message
                return responseEntity;
            }

            // Extract booking details from the response body
            Object responseBody = responseEntity.getBody();
            Map<String, String> bookingDetails = new HashMap<>();
            if (responseBody instanceof String) {
                String[] parts = ((String) responseBody).split(":");
                // Parse booking details from the string
                // Add them to the bookingDetails map
            } else if (responseBody instanceof Map) {
                bookingDetails = (Map<String, String>) responseBody;
            }

            // Add the booking details to the list of booking responses
            bookingResponses.add(bookingDetails);
        }

        // If all bookings are successful, return success message along with booking details
        return ResponseEntity.ok().body(bookingResponses);
    }


//    @DeleteMapping("/trains/username/bookings/{bookingId}")
//    public  boolean cancelTicketByPNR(@PathVariable int bookingId){
//        return userBookingService.cancelTicketByBookingId(bookingId);
//    }
    @DeleteMapping("/{username}/bookings/{bookingId}")
    public ResponseEntity<String> cancelTicketByPNR(@PathVariable int bookingId) {
        boolean cancellationStatus = userBookingService.cancelTicketByBookingId(bookingId);

        if (cancellationStatus) {
            return ResponseEntity.ok("Ticket with booking ID " + bookingId + " cancelled successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket with booking ID " + bookingId + " not found.");
        }
    }

    @GetMapping("/seats/{trainId}")
    public ResponseEntity<List<Seat>> getAllSeatsByTrainId(@PathVariable String trainId) {
        List<Seat> seats = userBookingService.getAllFilledSeats(trainId);
        if (!seats.isEmpty()) {
            return ResponseEntity.ok(seats);
        } else {
            return ResponseEntity.notFound().build();
        }
    }






}
