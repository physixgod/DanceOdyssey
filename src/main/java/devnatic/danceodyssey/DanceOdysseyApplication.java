package devnatic.danceodyssey;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication

public class DanceOdysseyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DanceOdysseyApplication.class, args);
    }


}
