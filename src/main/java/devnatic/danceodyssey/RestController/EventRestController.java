package devnatic.danceodyssey.RestController;

import devnatic.danceodyssey.DAO.Entities.Competition;
import devnatic.danceodyssey.DAO.Entities.Dancer;
import devnatic.danceodyssey.DAO.Entities.Event;
import devnatic.danceodyssey.DAO.Entities.User;
import devnatic.danceodyssey.DAO.Repositories.EventRepository;
import devnatic.danceodyssey.Services.EventIServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping("/event")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class EventRestController {
    EventIServices eventIServices;
    @PostMapping("AddEvent")
    public Event AddEvent(@RequestBody Event  e) {
        return eventIServices.AddOrUpdateEvent(e);
    }
    @GetMapping("ShowEvents")
    public List<Event> ShowEvents(){
        return eventIServices.ShowEvents();
    }
    @GetMapping("showEventsDancers/{id}")
    public Set<Dancer> showEventsDancers(@PathVariable("id") int idEvent){
        return eventIServices.showEventDancers(idEvent);
    }
    @GetMapping("showEventsUsers/{id}")
    public Set<User> showEventsUsers(@PathVariable("id") int idEvent){
        return eventIServices.showEventUsers(idEvent);
    }

}
