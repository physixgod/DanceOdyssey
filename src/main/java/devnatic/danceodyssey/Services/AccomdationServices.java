package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Accommodation;
import devnatic.danceodyssey.DAO.Entities.Dancer;
import devnatic.danceodyssey.DAO.Entities.Event;
import devnatic.danceodyssey.DAO.Repositories.AccomodationRepository;
import devnatic.danceodyssey.DAO.Repositories.DancerRepository;
import devnatic.danceodyssey.DAO.Repositories.EventRepository;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class AccomdationServices implements AccomdationIServices{
    DancerRepository dancerRepository;
    AccomodationRepository accomodationRepository;
    EventRepository eventRepository;
    SendEmail sendEmail;

    @Override
    public Accommodation addAcc(int idDancer, Accommodation accommodation,int idEvent) {

        Dancer dancer = dancerRepository.findById(idDancer).get();
        Event event=eventRepository.findById(idEvent).get();
        accommodation.setEventAccom(event);
        accommodation.setHoster(dancer);
        accommodation.setAvailabelity(true);

        return accomodationRepository.save(accommodation);
    }

    @Override
    public String registerAcc(int idDancer, int idAcc, int nbPersonnes, Boolean br, Boolean lunch, Boolean dinner) {
        Dancer dancer =dancerRepository.findById(idDancer).get();
        Accommodation accommodation=accomodationRepository.findById(idAcc).get();
        dancer.getResidentsAcoomadtions().add(accommodation);
        double totalPrice=0;
        totalPrice=accommodation.getPricePerNight()*nbPersonnes;
        if (br){
            totalPrice=totalPrice+accommodation.getBreakfeastPrice()*nbPersonnes;
        }
        if (lunch){
            totalPrice=totalPrice+accommodation.getLunchPrice()*nbPersonnes;
        }
        if (dinner){
            totalPrice=totalPrice+accommodation.getDinnerPrice()*nbPersonnes;
        }
        accommodation.setAvailableSlots(accommodation.getAvailableSlots()-nbPersonnes);
        if (accommodation.getAvailableSlots()<0){
            return "Not enough available slots for your request";
        }
        accomodationRepository.save(accommodation);
        /*{
        Send Email to the hoster to inform him about the request
         }*/
        return Double.toString(totalPrice);


    }

    @Override
    public Set<Accommodation> showEventAccomadations(int idEvent) {
        Set<Accommodation> accommodations=new HashSet<>();
        for (Accommodation accommodation:accomodationRepository.findAll()){
            if (accommodation.getEventAccom().getEventID()==idEvent){
                accommodations.add(accommodation);
            }
        }
        Set<Accommodation> output=new HashSet<>();
        for (Accommodation accommodation:accommodations){
            if (accommodation.getAvailabelity()){
                output.add(accommodation);
            }
        }
        return output;
    }

    @Override
    public String showAccPrice(int idAcc, int nbPersonnes, Boolean br, Boolean lunch, Boolean dinner) {
        Accommodation accommodation=accomodationRepository.findById(idAcc).get();
        double totalPrice=0;
        totalPrice=accommodation.getPricePerNight()*nbPersonnes;
        if (br){
            totalPrice=totalPrice+accommodation.getBreakfeastPrice()*nbPersonnes;
        }
        if (lunch){
            totalPrice=totalPrice+accommodation.getLunchPrice()*nbPersonnes;
        }
        if (dinner){
            totalPrice=totalPrice+accommodation.getDinnerPrice()*nbPersonnes;
        }
        return Double.toString(totalPrice);
    }

    @Override
    public String registerAcc(int idDancer, int idAcc, int nbPersonnes) {
        Dancer dancer =dancerRepository.findById(idDancer).get();
        Accommodation accommodation=accomodationRepository.findById(idAcc).get();
        String hosterEmail=accommodation.getHoster().getEmail();
        String hosterName=accommodation.getHoster().getFirstName()+" "+accommodation.getHoster().getLastName();
        dancer.getResidentsAcoomadtions().add(accommodation);

        accommodation.setAvailableSlots(accommodation.getAvailableSlots()-nbPersonnes);
        if (accommodation.getAvailableSlots()<0){
            return "Not enough available slots for your request";
        }
        accomodationRepository.save(accommodation);
        if(accommodation.getAvailableSlots()==0){
            accommodation.setAvailabelity(false);
            accomodationRepository.save(accommodation);
        }
        try {
            String message = "<html><body>" +
                    "<p>Dear " + hosterName + ",</p>" +
                    "<br/>" +
                    "<p>We are pleased to inform you that a new reservation has been made for your accommodation by Mr/Ms " +
                    dancer.getFirstName() + " " + dancer.getLastName() + ".</p>" +
                    "<br/>" +
                    "<p><b>Reservation Details:</b></p>" +
                    "<ul>" +
                    "<li>Number of persons: " + nbPersonnes + "</li>" +
                    "<li>BreakFast Included </li>" +
                    "<li>Lunch Included </li>" +
                    "<li>Dinner Included </li>" +
                    "</ul>" +
                    "<p>Please ensure all necessary arrangements are in place to welcome our guest.</p>" +
                    "<br/>" +
                    "<p>Thank you for your cooperation.</p>" +
                    "<br/>" +
                    "<p>Best Regards,<br/>DanceOdyssey Team</p>" +
                    "</body></html>";
            sendEmail.sendSimpleMessage(hosterEmail, "Reservation Notification", message);

        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to send email";
        }
        return "You place has been reserved";

    }


}
