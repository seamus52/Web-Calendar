package webCalendarSpring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/event")
public class EventController {
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        if (event.event() == null || event.event().isEmpty() || event.event().isBlank()
                || event.date() == null || event.date().toString().isEmpty() || event.date().toString().isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(Map.of("message", "The event has been added!", "event", event.event(),
                "date", event.date()), HttpStatus.OK);
    }
    @GetMapping("/today")
    public ResponseEntity<?> today() {
        return new ResponseEntity<>(List.of(), HttpStatus.OK);
    }
}
