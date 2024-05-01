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
public class UserBookingController {

    private final UserBookingService userBookingService;


    public UserBookingController(UserBookingService userBookingService) {
        this.userBookingService = userBookingService;
    }
    @GetMapping
    public ResponseEntity<List<Train>> getALlTrains(){
        return ResponseEntity.ok(userBookingService.getAllAvailableTrains());
    }

    @GetMapping("/station/{station}")
    public ResponseEntity<List<Train>> getAllByStation(@PathVariable("station") String station){
        List<Train> train = userBookingService.getTrainDetailsByStation(station);
        if (train.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userBookingService.getTrainDetailsByStation(station));
    }
    @GetMapping("/{username}/bookings")  // give username then get the booking on him
    public List<Booking> getAllBookingByUserName(@PathVariable String username){
        return userBookingService.getAllBookingsByUserName(username);
    }
    @GetMapping("/{PNR}")
    public List<Booking> getDetailsByPNR(@PathVariable String PNR){
        return userBookingService.getDetailsByPNR(PNR);
    }
    @PostMapping("/{trainId}/bookings")
    public  ResponseEntity<String> addBookingDetails(@PathVariable String trainId, @RequestBody BookingRequest bookingRequest){
           if(userBookingService.addBookingForTrain(trainId,bookingRequest)){
               return new ResponseEntity<>("Seat Registered successfully",HttpStatus.CREATED);
           }
           return new ResponseEntity<>("Seat Not Available",HttpStatus.BAD_REQUEST);
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
