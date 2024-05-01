package org.mohan.com.railwaybookingapp.repository;

import org.mohan.com.railwaybookingapp.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    Optional<Seat> findAllByTrainTrainId(String trainId);
    boolean deleteByBookingId(int id);
    Seat findByBookingId(int id);
    Seat findBySectionAndSeatTypeAndSeatNumber(String section, String seatType, int seatNumber);
   Optional<Seat> findFirstByTrainTrainIdAndAvailableTrue(String trainId);

}
