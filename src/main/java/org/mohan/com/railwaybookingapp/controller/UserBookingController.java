package org.mohan.com.railwaybookingapp.controller;


import org.mohan.com.railwaybookingapp.model.Booking;
import org.mohan.com.railwaybookingapp.model.BookingRequest;
import org.mohan.com.railwaybookingapp.model.Train;
import org.mohan.com.railwaybookingapp.service.UserBookingService;
import org.mohan.com.railwaybookingapp.service.impl.UserBookingImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/{trainId}/bookings")
    public  ResponseEntity<String> addBookingDetails(@PathVariable String trainId, @RequestBody List<BookingRequest> bookingRequest){
      for(BookingRequest bookingRequest1 : bookingRequest) {
          if (!userBookingService.addBookingForTrain(trainId, bookingRequest1)) {
              return new ResponseEntity<>("Seat Not Available",HttpStatus.BAD_REQUEST);

          }
      }
        return new ResponseEntity<>("Seat Registered successfully", HttpStatus.CREATED);
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





}
