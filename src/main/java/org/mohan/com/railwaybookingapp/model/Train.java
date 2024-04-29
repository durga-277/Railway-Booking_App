package org.mohan.com.railwaybookingapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Train {
    @Id
    private String trainId;
    private  String trainName;
    private  String sourceStation;
    private  String destinationStation;
    private int seats;
    private int availableSeats;
    private double ticketPrice;

}
