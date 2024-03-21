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
    public Event AddEventByDancer(Event e ,int idDancer);
    public Set<Event> showMyCreatedEvents(int idDancer);
    public Event getEventById(int id );
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2);
    public List<Event> getEventsNearLocation(double yourLatitude, double yourLongitude, double maxDistance);
}
