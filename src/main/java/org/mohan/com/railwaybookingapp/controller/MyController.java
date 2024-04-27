package org.mohan.com.railwaybookingapp.controller;


import org.mohan.com.railwaybookingapp.model.Train;
import org.mohan.com.railwaybookingapp.service.TrainInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trains")
public class MyController {

        private final TrainInterface trainInterface;

        public MyController(TrainInterface trainInterface) {
                this.trainInterface = trainInterface;
        }

        @GetMapping
        public ResponseEntity<List<Train>> getALl(){
            return ResponseEntity.ok(trainInterface.getAll());
        }
        @GetMapping("/{id}")
        public ResponseEntity<Train> getAllById(@PathVariable("id") String id){
                Train train = trainInterface.getTrainDetailsByID(id);
                if (train == null) {
                        return ResponseEntity.notFound().build(); // Return 404 Not Found
                }
                return ResponseEntity.ok(trainInterface.getTrainDetailsByID(id));
        }

        @PostMapping
        public ResponseEntity<String> addTrains( @RequestBody  List<Train> train ){
                trainInterface.addTrain(train);
                return  new ResponseEntity<>("SuccessFully Added the List of Trains",HttpStatus.CREATED);

        }
        @PutMapping("/{id}")
        public ResponseEntity<String> updateTrain(@PathVariable("id") String id, @RequestBody Train train){
                if(trainInterface.updateTrain(id,train)) {
                        return ResponseEntity.ok("SuccessFully Updated List of Trains");
                }
                return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        }
        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteTrain(@PathVariable("id") String id){
                if(trainInterface.deleteTrainById(id)) {
                        return ResponseEntity.ok("SuccessFully Deleted The Trains From the List");
                }
                return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        }


}
