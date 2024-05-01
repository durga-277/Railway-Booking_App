package org.mohan.com.railwaybookingapp.controller;


import jakarta.validation.Valid;
import org.mohan.com.railwaybookingapp.model.Train;
import org.mohan.com.railwaybookingapp.service.impl.TrainImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/trains")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
        
        private final TrainImpl trainImpl;

        public AdminController(TrainImpl trainImpl) {
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
        public ResponseEntity<String> addTrains(@Valid @RequestBody List<Train> trainList, BindingResult result) {
                if (result.hasErrors()) {
                        return ResponseEntity.badRequest().body("Validation failed: " + result.getAllErrors().get(0).getDefaultMessage());
                }
                if (trainList.isEmpty()) {
                        return ResponseEntity.badRequest().body("Train list cannot be empty");
                }
                trainImpl.addTrain(trainList);
                return ResponseEntity.status(HttpStatus.CREATED).body("Successfully added the list of trains");
        }
        @PutMapping("/{id}")
        public ResponseEntity<String> updateTrain(@PathVariable("id") String id, @RequestBody Train train){
                if(trainImpl.updateTrain(id,train)) {
                        return ResponseEntity.ok("Successfully Updated List of Trains");
                }
                return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        }
        @PostMapping("/seats/{id}")
        public ResponseEntity<String> updateTrainSeats(@PathVariable("id") String id){
                if (trainImpl.createSeatsForTrain(id)){
                        return new ResponseEntity<>("Successfully created seats",HttpStatus.CREATED);
                }
               return new ResponseEntity<>("Train id not found",HttpStatus.NOT_FOUND);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteTrain(@PathVariable("id") String id){
                if(trainImpl.deleteTrainById(id)) {
                        return ResponseEntity.ok("SuccessFully Deleted The Trains From the List");
                }
                return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        }


}
