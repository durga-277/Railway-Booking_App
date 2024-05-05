package org.mohan.com.railwaybookingapp.service.impl;

import org.mohan.com.railwaybookingapp.model.Seat;
import org.mohan.com.railwaybookingapp.model.Train;
import org.mohan.com.railwaybookingapp.repository.SeatRepository;
import org.mohan.com.railwaybookingapp.repository.TrainRepository;
import org.mohan.com.railwaybookingapp.service.TrainInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PrimitiveIterator;

@Service
public class TrainImpl implements TrainInterface {
//    private final List<Train> trains = new ArrayList<>();
        TrainRepository trainRepository;
        @Autowired
        SeatRepository seatRepository;

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


    public boolean createSeatsForTrain(String trainId) {
        Train train = trainRepository.findTrainByTrainId(trainId);
        if (train == null) {
            // Handle train not found error
            return false;
        }
        int totalSeats = 30;
        String[] sections = {"A", "B", "C", "D", "E"};
        String[] seatTypes = {"LLB", "LUB", "RLB", "RUB", "SLB", "SUB"};
        int seatsPerSection = totalSeats / (sections.length * seatTypes.length); // Calculate seats per section
        boolean flag = false;
        for (String section : sections) {
            for (int seatNumber = 1; seatNumber <= seatsPerSection; seatNumber++) {
                for (String seatType : seatTypes) {
                    Seat seat = new Seat();
                    seat.setTrain(train); // Associate the seat with the train
                    seat.setSection(section);
                    seat.setSeatType(seatType);
                    seat.setSeatNumber(seatNumber);
                    seat.setPrice(450.00); // seat price removed some handling issues
                    seat.setAvailable(true);// Set initial availability to true
                    seatRepository.save(seat); // Save the seat entity
                }
            }
            flag = true;
        }
        return flag;
    }

    @Override
    public List<String> getAllDestinationStations() {
        return trainRepository.findAllDestinationStations();
    }
    public List<String> getALlSourceStations(){
        return trainRepository.findAllSourceStation();
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
            seatRepository.deleteByTrainTrainId(id);
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
