package devnatic.danceodyssey.RestController;

import devnatic.danceodyssey.RealTimeNotifications.RealNotifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RealNotificationsController {
    @Autowired
    private SimpMessagingTemplate template;
    // Initialize Notifications
    private RealNotifications notifications = new RealNotifications(0);
    @GetMapping("/notify")
    public String getNotification() {
        System.err.println("gg");
        // Increment Notification by one
        notifications.increment();
        // Push notifications to front-end
        template.convertAndSend("/topic/notification", notifications);
        return "Notifications successfully sent to Angular !";
    }
}
