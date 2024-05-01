package org.mohan.com.railwaybookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@Getter
@Setter
public class SeatRequest {
    private String section;
    private String seatType;
    private int seatNumber;
}
