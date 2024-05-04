package devnatic.danceodyssey.Schedule;

import devnatic.danceodyssey.Services.IUserServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class UpdatingStatus {
    IUserServices iUserServices;
    @Scheduled(fixedRate = 30000)
    public void updateuser(){
        iUserServices.closeSub();
    }
}
