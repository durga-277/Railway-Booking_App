package org.mohan.com.railwaybookingapp.repository;

import org.mohan.com.railwaybookingapp.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train,String> {
    List<Train> findBySourceStationOrDestinationStation(String sourceStation, String destinationStation);
}
