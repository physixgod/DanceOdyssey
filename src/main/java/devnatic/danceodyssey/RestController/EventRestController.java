package devnatic.danceodyssey.RestController;

import devnatic.danceodyssey.DAO.Entities.Competition;
import devnatic.danceodyssey.DAO.Entities.Dancer;
import devnatic.danceodyssey.DAO.Entities.Event;
import devnatic.danceodyssey.DAO.Entities.User;
import devnatic.danceodyssey.DAO.Repositories.EventRepository;
import devnatic.danceodyssey.Services.EventIServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RequestMapping("/event")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class EventRestController {
    EventIServices eventIServices;

    @PostMapping("AddEvent")
    public Event AddEvent(@RequestBody Event e) {
        return eventIServices.AddOrUpdateEvent(e);
    }

    @GetMapping("ShowEvents")
    public List<Event> ShowEvents() {
        return eventIServices.ShowEvents();
    }

    @GetMapping("showEventsDancers/{id}")
    public Set<Dancer> showEventsDancers(@PathVariable("id") int idEvent) {
        return eventIServices.showEventDancers(idEvent);
    }

    @GetMapping("showEventsUsers/{id}")
    public Set<User> showEventsUsers(@PathVariable("id") int idEvent) {
        return eventIServices.showEventUsers(idEvent);
    }

    @PostMapping("AddEventByDancer/{idDancer}")
    public Event AddEventByDancer(@PathVariable("idDancer") int idDancer, @RequestBody Event event) {
        return eventIServices.AddEventByDancer(event, idDancer);
    }

    @GetMapping("MyCreatedEvents/{idDancer}")
    public Set<Event> showMyCreatedEvents(@PathVariable("idDancer") int idDancer) {
        return eventIServices.showMyCreatedEvents(idDancer);
    }

    @GetMapping("getEventById/{id}")
    public Event getEventById(@PathVariable("id") int id) {
        return eventIServices.getEventById(id);
    }

    @PutMapping("DeleteEvent/{id}")
    public void DeleteEvent(@PathVariable("id") int id) {
        eventIServices.DeleteEvent(id);
    }

    @GetMapping("nearbyEvents")
    public List<Event> getEventsNearby(@RequestParam double yourLatitude, @RequestParam double yourLongitude, @RequestParam double maxDistance) {
        return eventIServices.getEventsNearLocation(yourLatitude, yourLongitude, maxDistance);
    }
    @GetMapping("getEventImage/{id}")
    public String getCompetitionImage(@PathVariable("id") int idEvent){
        return eventIServices.getImageUrlForEventByID(idEvent);
    }
    @PostMapping("uploadEventImage/image/{id}")
    public Event updateCompetitionImage(@PathVariable("id")int idEvent, @RequestParam("image") MultipartFile image){
        return eventIServices.updateEventImage(idEvent,image);
    }
    @PostMapping("registerDancerEvent/{idDancer}/{idEvent}")
    public Boolean registerDancerEvent(@PathVariable("idDancer")int idDancer,@PathVariable("idEvent") int idEvent){
       return eventIServices.dancerRegisterAtEvent(idDancer,idEvent);
    }
}
