package org.mohan.com.railwaybookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private  String bookingId;
    private  String PNR;
    private LocalDateTime BookingDateTime;
    private  String name;
    private  int age;
    private String userName;
    private int seatType;
    private double price;
    private String seatSection;
}
