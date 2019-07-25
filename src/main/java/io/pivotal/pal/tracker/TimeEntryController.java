package io.pivotal.pal.tracker;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class TimeEntryController {


    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;



    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }


    @PostMapping(value= "/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody  TimeEntry timeEntry){
        TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntry);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());

        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }


    @PutMapping(value= "/time-entries/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable Long id, @RequestBody TimeEntry entry){

        TimeEntry timeEntry = timeEntryRepository.update(id, entry);
        if(Objects.nonNull(timeEntry)){
            actionCounter.increment();
            return new ResponseEntity<>(timeEntry,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(timeEntry, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value= "/time-entries")
    public ResponseEntity<List<TimeEntry>> list(){
        actionCounter.increment();
        return new ResponseEntity<>(timeEntryRepository.list(),HttpStatus.OK);
    }

    @GetMapping(value= "/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id){
        TimeEntry timeEntry = timeEntryRepository.find(id);
        if(Objects.nonNull(timeEntry)){
            actionCounter.increment();
            return new ResponseEntity<>(timeEntry,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(timeEntry, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value= "/time-entries/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        timeEntryRepository.delete(id);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
