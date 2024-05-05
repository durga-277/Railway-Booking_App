package org.mohan.com.railwaybookingapp.repository;

import org.mohan.com.railwaybookingapp.model.Seat;
import org.mohan.com.railwaybookingapp.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    Optional<Seat> findAllByTrainTrainId(String trainId);
    boolean deleteByBookingId(int id);

//    int getSeatByPriceOrderByBookingId();
    @Transactional
    void deleteByTrainTrainId(String trainId);
    void deleteAllByTrainTrainId(String id);
//    void deleteByTrain_TrainId(String id);
    Seat findByBookingId(int id);
    Seat findBySectionAndSeatTypeAndSeatNumberAndTrain_TrainId(String section, String seatType, int seatNumber, String trainId);
    Seat findBySectionAndSeatTypeAndSeatNumber(String section, String seatType, int seatNumber);
   Optional<Seat> findFirstByTrainTrainIdAndAvailableTrue(String trainId);

//    Optional<Seat> findAllByTrainTrainId(String trainId);

    List<Seat> findAllByAvailableIsFalseAndTrain_TrainId(String trainId);

}
