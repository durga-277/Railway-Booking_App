package org.mohan.com.railwaybookingapp.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.mohan.com.railwaybookingapp.model.*;
import org.mohan.com.railwaybookingapp.repository.BookingRepository;
import org.mohan.com.railwaybookingapp.repository.SeatRepository;
import org.mohan.com.railwaybookingapp.repository.TrainRepository;
import org.mohan.com.railwaybookingapp.repository.UserRepository;
import org.mohan.com.railwaybookingapp.service.UserBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserBookingImpl implements UserBookingService {
    @Autowired
    TrainRepository trainRepository;   // this is for train details

    @Autowired
    SeatRepository seatRepository;
//    public UserBookingImpl(TrainRepository trainRepository) {
//        this.trainRepository = trainRepository;
//    }
    @Autowired
    BookingRepository bookingRepository; // this is for booking details

//    public UserBookingImpl(BookingRepository bookingRepository) {
//        this.bookingRepository = bookingRepository;
//    }
    @Autowired
    UserRepository userRepository;
//    public UserBookingImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public List<Train> getAllAvailableTrains() {
        return trainRepository.findAll();
    }

    @Override
    public List<Booking> getAllBookingsByUserName(String userName) {
//        Optional<User> optionalUser = userRepository.findByUsername(userName);
//        if(optionalUser.isPresent()) {
//            User user = optionalUser.get();    // try after if not works
//        }
            return bookingRepository.findAllByUserUsername(userName);
    }


    @Override
    public boolean addBookingForTrain(String trainId, BookingRequest bookingRequest) {
        Train train = trainRepository.findTrainByTrainId(trainId);
        if (train == null) {
            // Handle train not found error
            return false;
        }

        // Save user information
        User user = userRepository.findByUsername(bookingRequest.getUsername())
                .orElseGet(() -> userRepository.save(new User(1, bookingRequest.getUsername())));

        // If seat information is provided, assign the seat to the booking
        if (bookingRequest.getSeat() != null) {
            SeatRequest seatRequest = bookingRequest.getSeat();
            Seat seat = seatRepository.findBySectionAndSeatTypeAndSeatNumber(
                    seatRequest.getSection(),
                    seatRequest.getSeatType(),
                    seatRequest.getSeatNumber()
            );

            if (seat == null || !seat.isAvailable()) {
                // Handle seat not available error or seat not found error
                return false;
            }

            // Check if the seat is already assigned to the same user
            if (seat.getUsername() != null && seat.getUsername().equals(user.getUsername())) {
                // Seat is already assigned to the same user, do not allow booking
                // Handle error or return
                return false;
            }

            // Update seat information
            seat.setAvailable(false);
            seat.setUsername(user.getUsername());

            // Generate bookingId
            Booking booking = Booking.builder()
                    .name(bookingRequest.getName())
                    .gender(bookingRequest.getGender())
                    .age(bookingRequest.getAge())
                    .payment(bookingRequest.isPayment())
//                    .PNR(bookingRequest.getPNR())
                    .train(train)
                    .user(user)
                    .localDateTime(LocalDateTime.now())
                    .seat(seat)
                    .build();
            booking.generatePNR();

            bookingRepository.save(booking);

// Get the bookingId generated in the Booking entity
            int bookingId = booking.getBookingId();

// Set the bookingId in the associated seat entity
            seat.setBookingId(bookingId);

// Save the seat entity in the seat repository
            seatRepository.save(seat);
            // Save the booking entity to the database
            bookingRepository.save(booking);
        }
        return true;
    }


    private Seat findAvailableSeatForTrain(Train train) {
        Optional<Seat> optionalSeat = seatRepository.findFirstByTrainTrainIdAndAvailableTrue(train.getTrainId());
//        Optional<Seat> optionalSeat = seatRepository.findAllByTrainTrainId(train.getTrainId());
        return optionalSeat.orElse(null);
    }

    public Train getAllTrainsByTrainId(String id){
        Optional<Train> optionalTrain = trainRepository.findById(id);
        return optionalTrain.orElse(null);
    }


    @Override
    public List<Booking> getDetailsByPNR(String pnr) {
        return bookingRepository.findBookingByPNR(pnr);
    }

    public List<Train> getTrainDetailsByStation(String source,String destination){
        return trainRepository.findBySourceStationAndDestinationStation(source,destination);
    }


//    @Override
//    public boolean cancelTicketByBookingId(int bookingId) {
//        if(bookingRepository.deleteByBookingId(bookingId)){
//            return true;
//        }
//        return false;
//    }
//    @PersistenceContext
//    private EntityManager entityManager;
    @Transactional
    public boolean cancelTicketByBookingId(int bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findBookingByBookingId(bookingId);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();

            // Delete the booking
            bookingRepository.delete(booking);

            // Mark the seat as available
            Seat seat = booking.getSeat();
            if (seat != null) {
                seat.setAvailable(true);
                    seat.setUsername(null);
                    seat.setBookingId(0);
                seatRepository.save(seat);
            }
        } else {
            return false; // Booking with given bookingId not found
        }

        return true; //
    }
}
