package devnatic.danceodyssey.Schedular;

import devnatic.danceodyssey.Services.CompetitionIServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class UpdateEventsStatus {
    CompetitionIServices competitionIServices;
    @Scheduled(fixedRate = 10000)
    public void updateCompetitionStatus(){
        competitionIServices.autoCloseCompetition();
    }
}
