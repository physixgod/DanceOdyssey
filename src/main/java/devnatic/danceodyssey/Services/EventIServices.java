package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Dancer;
import devnatic.danceodyssey.DAO.Entities.Event;
import devnatic.danceodyssey.DAO.Entities.User;

import java.util.List;
import java.util.Set;

public interface EventIServices {
    public Event AddOrUpdateEvent(Event e);
    public void DeleteEvent(int idEvent );
    public List<Event> ShowEvents();
    public Set<Dancer> showEventDancers(int idEvent);
    public Set<User> showEventUsers(int idEvent);
}
