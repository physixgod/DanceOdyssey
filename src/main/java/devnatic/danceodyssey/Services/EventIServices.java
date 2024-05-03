package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Competition;
import devnatic.danceodyssey.DAO.Entities.Dancer;
import devnatic.danceodyssey.DAO.Entities.Event;
import devnatic.danceodyssey.DAO.Entities.User;
import org.springframework.web.multipart.MultipartFile;

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
    public Event updateEventImage(int idEvent, MultipartFile eventImage);
    public String getImageUrlForEventByID(int idEvent);
    public Boolean dancerRegisterAtEvent(int idDancer,int idEvent );
    public String userRegisterAtEvent(int idUser,int idEvent);

}
