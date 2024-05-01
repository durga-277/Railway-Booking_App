package org.mohan.com.railwaybookingapp.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Booking {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Define PNR as a unique 6-digit number
    @Column(unique = true)
    public String PNR;
    @Column(unique = true)
    public int bookingId;

    private String name;
    private String gender;
    private  int age;
    @ManyToOne
    private Seat seat;
    @ManyToOne
    private Train train;
    @ManyToOne
    private User user;
    private boolean payment;
    private LocalDateTime localDateTime;
    @Builder
    public Booking(String name, String gender, int age, boolean payment) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.payment = payment;
        generateBookingId();
        generatePNR();
    }

    public void generatePNR() {
        Random random = new Random();
        int pnrNumber = random.nextInt(900000) + 100000;
        this.PNR = String.valueOf(pnrNumber);

    }
    @PrePersist
    public void generateBookingId(){
        Random random = new Random();
        this.bookingId = random.nextInt(100)+1000;
    }

}
