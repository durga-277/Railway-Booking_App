package org.mohan.com.railwaybookingapp.service;

import org.mohan.com.railwaybookingapp.model.Train;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

@Service
public class TrainImpl implements TrainInterface{
    private final List<Train> trains = new ArrayList<>();

    @Override
    public List<Train> getAll() {
            return trains;

    }

    @Override
    public void addTrain(List<Train> trainList) {
        trains.addAll(trainList);
    }

    @Override
    public boolean updateTrain(String id,Train train) {
        for(Train train1 : trains){
            if(train1.getTrainId().equals(id)){
                train1.setTrainName(train.getTrainName());
                train1.setDestinationStation(train.getDestinationStation());
                train1.setSourceStation(train.getSourceStation());
                train1.setSeats(train.getSeats());
                train1.setAvailableSeats(train.getAvailableSeats());
                train1.setTicketPrice(train.getTicketPrice());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteTrainById(String id) {
        for (Train train : trains){
            if(train.getTrainId().equals(id)){
                return trains.remove(train);
            }
        }
        return false;
    }

    @Override
    public Train getTrainDetailsByID(String id) {
        for(Train train : trains){
            if(train.getTrainId().equals(id)){
                return train;
            }
        }
        return null;
    }

    @Override
    public List<Train> getTrainDetails(String text) {
        List<Train> trainList = new ArrayList<>();
        for(Train train : trains){
            if(train.getSourceStation().equals(text) || train.getDestinationStation().equals(text)){
                trainList.add(train);
            }
        }
        return trainList;
    }

}
