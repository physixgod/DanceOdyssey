    package devnatic.danceodyssey.Services;

    import devnatic.danceodyssey.DAO.Entities.Competition;
    import devnatic.danceodyssey.DAO.Entities.Dancer;
    import devnatic.danceodyssey.DAO.Entities.Event;
    import devnatic.danceodyssey.DAO.Entities.User;
    import devnatic.danceodyssey.DAO.Repositories.DancerRepository;
    import devnatic.danceodyssey.DAO.Repositories.EventRepository;
    import lombok.AllArgsConstructor;
    import org.springframework.core.env.Environment;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;


    @org.springframework.stereotype.Service
    @AllArgsConstructor
    public class EventServices implements EventIServices {
        EventRepository eventRepository;
        DancerRepository dancerRepository;
        Environment environment;
        NameFile fileNamingUtil;
        FileUtil utils;

        public Event AddOrUpdateEvent(Event e) {
            if (e != null) {
                return eventRepository.save(e);
            }
            return e;
        }

        @Override
        public void DeleteEvent(int idEvent) {

           /* Event e = eventRepository.findById(idEvent).get();
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
          eventRepository.delete(e);*/
            Event e = eventRepository.findById(idEvent).get();
            e.setCancelled(Boolean.TRUE);
            eventRepository.save(e);
        }

        @Override
        public List<Event> ShowEvents() {
            List<Event> events = eventRepository.findAll();
            List<Event> output1 = new ArrayList<>();
            List<Event> output = new ArrayList<>();
            for (Event event : events) {
                if (event.getStartDate().isAfter(LocalDate.now())) {
                    output1.add(event);
                }
            }
            for (Event event : output1) {
                if (event.getCancelled().equals(false)) {
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

        @Override
        public Event AddEventByDancer(Event e, int idDancer) {
            Dancer dancer = dancerRepository.findById(idDancer).get();
            e.setCancelled(Boolean.FALSE);
            e.setCurrentParticipants(0);
            dancer.getEventsCreatedByDancers().add(e);
            dancerRepository.save(dancer);
            return eventRepository.save(e);

        }

        @Override
        public Set<Event> showMyCreatedEvents(int idDancer) {
            Set<Event> events = dancerRepository.findById(idDancer).get().getEventsCreatedByDancers();
            Set<Event> output = new HashSet<>();
            for (Event e : events) {
                if (e.getCancelled().equals(false)) {
                    output.add(e);
                }
            }
            return output;
        }

        @Override
        public Event getEventById(int id) {
            return eventRepository.findById(id).get();
        }

        public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
            double earthRadius = 6371; // Radius of the Earth in kilometers
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                            Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = earthRadius * c;

            return distance;
        }

        @Override
        public List<Event> getEventsNearLocation(double yourLatitude, double yourLongitude, double maxDistance) {

            List<Event> allEvents = ShowEvents();
            List<Event> eventsNearYou = new ArrayList<>();
            for (Event event : allEvents) {
                double distance = calculateDistance(yourLatitude, yourLongitude, event.getLatitude(), event.getLongitude());
                if (distance <= maxDistance) {
                    eventsNearYou.add(event);
                }
            }
            return eventsNearYou;
        }

        @Override
        public Event updateEventImage(int idEvent, MultipartFile eventImage) {
            Event event=eventRepository.findById(idEvent).get();
            try {
                if (eventImage != null && !eventImage.isEmpty() && eventImage.getSize() > 0) {
                    String uploadDir = environment.getProperty("upload.competition.images");
                    String newPhotoName = fileNamingUtil.nameFile(eventImage);
                    event.setEventImage(newPhotoName);
                    utils.saveNewFile(uploadDir, newPhotoName, eventImage);
                }
                return eventRepository.save(event);
            } catch (IOException e) {
                throw new RuntimeException("Failed to update event photo", e);
            }
        }

        @Override
        public String getImageUrlForEventByID(int idEvent) {
            Event event=eventRepository.findById(idEvent).get();
            String baseUrl = environment.getProperty("export.competition.images");
            String eventImage = event.getEventImage();
            if (eventImage != null && !eventImage.isEmpty()) {
                return baseUrl + eventImage;
            }
            return null;
        }

        @Override
        public Boolean dancerRegisterAtEvent(int idDancer, int idEvent) {
            Dancer dancer = dancerRepository.findById(idDancer).orElse(null);
            Event event = eventRepository.findById(idEvent).orElse(null);
            String msg = "";
            boolean test=false;
            if (dancer == null || event == null) {
                msg = "Invalid dancer or event ID";
            } else if (event.getDancers().contains(dancer)) {

            } else {
                event.getDancers().add(dancer);
                dancer.getEventsAttendedByDancers().add(event);
                event.setCurrentParticipants(event.getCurrentParticipants() + 1);

                eventRepository.save(event);
                dancerRepository.save(dancer);
                msg = "You have been registered for this event";
                test=true;
            }
            System.err.println(msg);
            return test;

        }

        @Override
        public String userRegisterAtEvent(int idUser, int idEvent) {
            return null;
        }


    }



