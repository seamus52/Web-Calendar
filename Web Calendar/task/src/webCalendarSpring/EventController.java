package webCalendarSpring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {
    @GetMapping("/today")
    public ResponseEntity<?> today() {
        return new ResponseEntity<>(new String[0], HttpStatus.OK);
    }
}
