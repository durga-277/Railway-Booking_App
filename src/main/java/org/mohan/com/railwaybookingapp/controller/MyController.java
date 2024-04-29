package org.mohan.com.railwaybookingapp.controller;


import org.mohan.com.railwaybookingapp.model.Train;
import org.mohan.com.railwaybookingapp.service.TrainImpl;
import org.mohan.com.railwaybookingapp.service.TrainInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trains")
@CrossOrigin(origins = "http://localhost:3000")
public class MyController {
        
        private final TrainImpl trainImpl;

        public MyController(TrainImpl trainImpl) {
                this.trainImpl = trainImpl;
        }

        @GetMapping
        public ResponseEntity<List<Train>> getALl(){
            
//                return ResponseEntity.ok(trainImpl.getAll());
                return ResponseEntity.ok(trainImpl.getAll());
        }
        @GetMapping("/{id}")
        public ResponseEntity<Train> getAllById(@PathVariable("id") String id){
                Train train = trainImpl.getTrainDetailsByID(id);
                if (train == null) {
                        return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(trainImpl.getTrainDetailsByID(id));
        }
        @GetMapping("/station/{station}")
        public ResponseEntity<List<Train>> getAllByStation(@PathVariable("station") String station){
                List<Train> train = trainImpl.getTrainDetailsByStation(station);
                if (train.isEmpty()) {
                        return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(trainImpl.getTrainDetailsByStation(station));
        }

        @PostMapping
        public ResponseEntity<String> addTrains( @RequestBody  List<Train> train ){
                trainImpl.addTrain(train);
                return  new ResponseEntity<>("SuccessFully Added the List of Trains",HttpStatus.CREATED);

        }
        @PutMapping("/{id}")
        public ResponseEntity<String> updateTrain(@PathVariable("id") String id, @RequestBody Train train){
                if(trainImpl.updateTrain(id,train)) {
                        return ResponseEntity.ok("SuccessFully Updated List of Trains");
                }
                return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        }
        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteTrain(@PathVariable("id") String id){
                if(trainImpl.deleteTrainById(id)) {
                        return ResponseEntity.ok("SuccessFully Deleted The Trains From the List");
                }
                return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        }


}
