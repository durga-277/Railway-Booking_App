package org.mohan.com.railwaybookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Train {

    private String trainId;
    private  String trainName;
    private  String sourceStation;
    private  String destinationStation;
    private int seats;
    private int availableSeats;
    private double ticketPrice;

}
