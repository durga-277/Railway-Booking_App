package org.mohan.com.railwaybookingapp.service;

import org.mohan.com.railwaybookingapp.model.Train;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TrainInterface {


     List<Train> getAll();

     void addTrain(List<Train> trains);

     Train getTrainDetailsByID(String id);

     List<Train> getTrainDetailsByStation(String station);

    boolean updateTrain(String id,Train train);

    boolean deleteTrainById(String id);

    boolean createSeatsForTrain(String trainId);

    List<String> getAllDestinationStations();
    List<String> getALlSourceStations();




}
