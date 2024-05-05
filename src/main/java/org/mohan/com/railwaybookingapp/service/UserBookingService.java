package org.mohan.com.railwaybookingapp.service;

import org.mohan.com.railwaybookingapp.model.Booking;
import org.mohan.com.railwaybookingapp.model.BookingRequest;
import org.mohan.com.railwaybookingapp.model.Seat;
import org.mohan.com.railwaybookingapp.model.Train;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserBookingService {


    List<Train> getAllAvailableTrains(); // show all the available trains by get all  method

    List<Booking> getAllBookingsByUserName(String userName);

    ResponseEntity<?> addBookingForTrain(String trainId, BookingRequest bookingRequest);
//   boolean addBookingForTrain(String trainId, BookingRequest bookingRequest);

     List<Booking> getDetailsByPNR(String PNR);
    boolean cancelTicketByBookingId(int bookingId);

     List<Train> getTrainDetailsByStation(String source,String destination);

     Train getAllTrainsByTrainId(String id);

    List<Seat> getAllFilledSeats(String trainId);




}
