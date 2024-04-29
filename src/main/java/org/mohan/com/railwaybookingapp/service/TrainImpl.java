package org.mohan.com.railwaybookingapp.service;

import org.mohan.com.railwaybookingapp.model.Train;
import org.mohan.com.railwaybookingapp.repository.TrainRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PrimitiveIterator;

@Service
public class TrainImpl implements TrainInterface{
//    private final List<Train> trains = new ArrayList<>();
        TrainRepository trainRepository;

    public TrainImpl(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @Override
    public List<Train> getAll() {
            return trainRepository.findAll();

    }

    @Override
    public void addTrain(List<Train> trainList) {
        trainRepository.saveAll(trainList);
    }

    @Override
    public boolean updateTrain(String id,Train train) {
        Optional<Train> optionalTrain = trainRepository.findById(id);

            if(optionalTrain.isPresent()){
                Train train1 = optionalTrain.get();
                train1.setTrainName(train.getTrainName());
                train1.setDestinationStation(train.getDestinationStation());
                train1.setSourceStation(train.getSourceStation());
                train1.setSeats(train.getSeats());
                train1.setAvailableSeats(train.getAvailableSeats());
                train1.setTicketPrice(train.getTicketPrice());
                trainRepository.save(train1);
                return true;
            }

        return false;
    }

    @Override
    public boolean deleteTrainById(String id) {
        try{
            trainRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public Train getTrainDetailsByID(String id) {
        Optional<Train> optionalTrain = trainRepository.findById(id);
        return optionalTrain.orElse(null);
    }

    @Override
    public List<Train> getTrainDetailsByStation(String station) {
        return trainRepository.findBySourceStationOrDestinationStation(station,station);
    }

}
