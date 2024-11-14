package com.insurancesimulator.config;

import static org.springframework.security.config.Customizer.withDefaults;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurancesimulator.utils.models.shared.ErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${creds.user.name}")
    String userName;

    @Value("${creds.user.password}")
    String userPass;

    @Value("${creds.admin.name}")
    String adminName;

    @Value("${creds.admin.password}")
    String adminPass;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    //TODO assign the customerId to specific user and
                    // let him access only his own insDetails
                    .requestMatchers("/api/insurance/{customerId}").hasAnyRole("USER", "ADMIN")
                    .anyRequest().hasRole("ADMIN")
            )
            .formLogin(formLogin ->
                formLogin
                    .defaultSuccessUrl("/api/insurance", true)
                    .permitAll()
            )

            .httpBasic(withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .logout(LogoutConfigurer::permitAll);

            http.exceptionHandling(exceptionHandling ->
                exceptionHandling.accessDeniedHandler(customAccessDeniedHandler())
            );

        return http.build();
    }

    private AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            ErrorResponse errorResponse = new ErrorResponse(
                "Access denied: You do not have permission to access this resource.",
                HttpStatus.FORBIDDEN.value()
            );
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(errorResponse));
        };
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.withUsername(userName)
            .password(userPass)
            .roles("USER")
            .build();

        UserDetails admin = User.withUsername(adminName)
            .password(adminPass)
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
