package io.pivotal.pal.tracker;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class TimeEntryController {


    TimeEntryRepository timeEntryRepository;


    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }


    @PostMapping(value= "/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody  TimeEntry entry){
        return new ResponseEntity<>(timeEntryRepository.create(entry),HttpStatus.CREATED);
    }


    @PutMapping(value= "/time-entries/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable Long id, @RequestBody TimeEntry entry){

        TimeEntry timeEntry = timeEntryRepository.update(id, entry);
        if(Objects.isNull(timeEntry)){
            return new ResponseEntity<>(timeEntry,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(timeEntry,HttpStatus.OK);
    }

    @GetMapping(value= "/time-entries")
    public ResponseEntity<List<TimeEntry>> list(){
        return new ResponseEntity<>(timeEntryRepository.list(),HttpStatus.OK);
    }

    @GetMapping(value= "/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id){
        TimeEntry timeEntry = timeEntryRepository.find(id);
        if(Objects.isNull(timeEntry)){
            return new ResponseEntity<>(timeEntry,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(timeEntry,HttpStatus.OK);

    }

    @DeleteMapping(value= "/time-entries/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        timeEntryRepository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
