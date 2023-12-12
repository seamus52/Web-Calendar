package webCalendarSpring;

import jakarta.annotation.Resource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/event")
public class EventController {
    @Resource
    EventRepository repository;
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        if (event.getEvent() == null || event.getEvent().isEmpty() || event.getEvent().isBlank()
                || event.getDate() == null || event.getDate().toString().isEmpty() || event.getDate().toString().isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        repository.save(event);
        return new ResponseEntity<>(Map.of("message", "The event has been added!", "event", event.getEvent(),
                "date", event.getDate()), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> listEvents(@RequestParam Optional<String> start_time, @RequestParam Optional<String> end_time) {
        List<Event> events;
        if (start_time.isPresent() && end_time.isPresent()) {
            events = repository.getAllBetweenDates(LocalDate.parse(start_time.get()), LocalDate.parse(end_time.get()));
        } else {
            events = repository.findAll();
        }

        if (events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable("id") int id) {
        Optional<Event> event = repository.findById(id);

        if (event.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "The event doesn't exist!"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEventById(@PathVariable("id") int id) {
        Optional<Event> event = repository.findById(id);

        if (event.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "The event doesn't exist!"), HttpStatus.NOT_FOUND);
        }

        repository.deleteById(id);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping("/today")
    public ResponseEntity<?> today() {
        Event exampleEvent = new Event();
        exampleEvent.setDate(LocalDate.now());
        // id is set automatically, we need to ignore it for QBE
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id");
        Example<Event> example = Example.of(exampleEvent, matcher);
        List<Event> events = repository.findAll(example);

        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}
