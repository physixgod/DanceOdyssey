package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Dancer;
import devnatic.danceodyssey.DAO.Entities.Event;
import devnatic.danceodyssey.DAO.Entities.User;
import devnatic.danceodyssey.DAO.Repositories.EventRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class EventServices implements EventIServices {
    EventRepository eventRepository;

    public Event AddOrUpdateEvent(Event e) {
        if (e != null) {
            return eventRepository.save(e);
        }
        return e;
    }

    @Override
    public void DeleteEvent(int idEvent) {

        Event e = eventRepository.findById(idEvent).get();
        if (!e.getDancers().isEmpty() && !e.getUsers().isEmpty()) {
            for (Dancer d : e.getDancers()) {
                String msg = " Sorry Dear " + d.getFirstName() + " " + d.getLastName() +
                        " , We are Sorry to inform you that the event " + e.getEventName() +
                        " has been cancelled";
            }
            for (User u : e.getUsers()) {
                String msg = " Sorry Dear " + u.getFirstName() + " " + u.getLastName() +
                        " , We are Sorry to inform you that the event " + e.getEventName() +
                        " has been cancelled";
            }
        }
      eventRepository.delete(e);
    }

    @Override
    public List<Event> ShowEvents() {
        List<Event> events = eventRepository.findAll();
        List<Event> output=new ArrayList<>();
        for (Event event:events){
            if (event.getStartDate().isBefore(LocalDate.now())){
                output.add(event);
            }
        }
        return output;
    }

    @Override
    public Set<Dancer> showEventDancers(int idEvent) {
        return eventRepository.findById(idEvent).get().getDancers();
    }

    @Override
    public Set<User> showEventUsers(int idEvent) {
        return eventRepository.findById(idEvent).get().getUsers();
    }
}



