package org.mohan.com.railwaybookingapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int bookingId;
    @ManyToOne
    private Train train;
    private String section; // A, B, C, D, or E
    private String seatType; // LW1, LW2, UP1, UP2, SL, SU
    private int seatNumber; // Seat number within the section (1 to 6 for each section)
    private String username;
    private boolean available; //


}
