package org.mohan.com.railwaybookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BookingRequest {
    private String name;
    private String gender;
    private int age;
    private boolean payment;
    private String username;
    private SeatRequest seat;
    private String PNR;  // here add this

    public SeatRequest getSeat() {
        return seat;
    }

    public void setSeat(SeatRequest seat) {
        this.seat = seat;
    }
}
