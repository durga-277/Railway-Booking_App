package org.mohan.com.railwaybookingapp.repository;

import jakarta.validation.constraints.NotBlank;
import org.mohan.com.railwaybookingapp.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train,String> {
    List<Train> findBySourceStationAndDestinationStation(String sourceStation, String destinationStation);

   List<Train> findBySourceStationOrDestinationStation(String sourceStation,String destinationStation);
    Train findTrainByTrainId(String id);
    @Query("SELECT DISTINCT t.destinationStation FROM Train t")
    List<String> findAllDestinationStations();
    @Query("SELECT DISTINCT t.sourceStation FROM Train t")
    List<String> findAllSourceStation();
}
