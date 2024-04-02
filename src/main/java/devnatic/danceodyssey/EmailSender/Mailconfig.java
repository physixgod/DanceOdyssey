package devnatic.danceodyssey.EmailSender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class Mailconfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); // Set your SMTP host
        mailSender.setPort(587); // Set your SMTP port
        mailSender.setUsername("alayamontassar7@gmail.com"); // Set your email address
        mailSender.setPassword("upaj bryt iwup wdvz"); // Set your email password
        Properties props = mailSender.getJavaMailProperties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Additional mail properties configuration if needed
        // For example:
        // mailSender.setJavaMailProperties(javaMailProperties());

        return mailSender;
    }

    // Optionally, you can define additional mail properties if needed
    // private Properties javaMailProperties() {
    //     Properties properties = new Properties();
    //     properties.put("mail.smtp.auth", "true");
    //     properties.put("mail.smtp.starttls.enable", "true");
    //     return properties;
    // }
}