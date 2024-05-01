package org.mohan.com.railwaybookingapp.repository;

import jakarta.persistence.Id;
import org.mohan.com.railwaybookingapp.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findAllByUserUsername(String username);
    List<Booking> findBookingByPNR(String pnr);
    Optional<Booking> findBookingByBookingId(int id);
}
