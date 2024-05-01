package org.mohan.com.railwaybookingapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Train ID cannot be blank")
    public String trainId;
    @NotBlank(message = "Train name cannot be blank")
    private  String trainName;
    @NotBlank(message = "sourceStation cannot be blank")
    private  String sourceStation;
    @NotBlank(message = "destinationStation cannot be blank")
    private  String destinationStation;
    private int seats;
    private int availableSeats;
    private double ticketPrice;

}
