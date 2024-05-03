package devnatic.danceodyssey.config;

import devnatic.danceodyssey.Services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter;

    // User Creation
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoService();
    }

    // Configuring HttpSecurity
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .cors() // Enable CORS
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/user/addNewUser", "/user/generateToken", "/roles", "/user/showusers",
                        "/user/forgotPassword", "/user/resetPassword", "/user/updateJuryCV/image/*",
                        "/user/getJuryCV/*", "/competition/ShowCompetitions", "/competition/AddCompetitionorUpdate",
                        "/jury/addJury", "/competition/DeleteCompetition/*", "/competition/SearchCompetitionByName/*",
                        "/competition/SearchCompetitionByLocation/*", "/competition/SearchCompetitionStartDate/*",
                        "/competition/SeachCompetitionByDanceCategory/*", "/competition/UpdateCompetitionStatus/*",
                        "/competition/showCompetitonDancers/*", "/competition/showDancersRank/*",
                        "/competition/showClosedCompetition", "/competition/getcp/*", "/competition/Register/*/*",
                        "/competition/getCompetitionDancers/*", "/competition/getMyCompetitions/*",
                        "/competition/getCompetitionImage/*", "/competition/uploadCompetitionImage/image/*",
                        "/event/AddEvent", "/event/ShowEvents", "/event/showEventsDancers/*", "/event/showEventsUsers/*",
                        "/event/AddEventByDancer/*", "/event/MyCreatedEvents/*", "/event/getEventById/*",
                        "/event/DeleteEvent/*", "/event/nearbyEvents", "/event/getEventImage/*",
                        "/event/uploadEventImage/image/*", "/event/registerDancerEvent/*/*", "/jury/addJury",
                        "/jury/updatejury/*", "/jury/getAll", "/jury/getjurybyid/*", "/jury/deletejury/*",
                        "/jury/juryManagers/search", "/jury/approvejury/*", "/jury/rejectjury/*", "/jury/competitions",
                        "/jury/setJury/*/*", "/jury/showApprovedJuries", "/jury/showNotAffectedJuries/*",
                        "/jury/showAffectedJuries/*", "/jury/SearchJuryByName/*", "/jury/SearchJuries/*/*",
                        "/jury/*/image", "/jury/uploadImage/*", "/jury/*/uploadExcel", "/jury/Noteparticipants",
                        "/jury/MyJuryCompetition/*", "/question/getRandomQuestion", "/event/AddACC/*/*",
                        "/event/registerACC/*/*", "/event/ShowAcc/*", "/event/register/*/*",
                        "/event/GetPrice/*", "/jury/*/uploadExcel", "/jury/participants/*/details",
                        "/jury/*/downloadExcel","/competition/gainPoints/*").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/user/admin/**").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Password Encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // Allow requests from all origins
        configuration.addAllowedMethod("*"); // Allow all HTTP methods
        configuration.addAllowedHeader("*"); // Allow all headers
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
