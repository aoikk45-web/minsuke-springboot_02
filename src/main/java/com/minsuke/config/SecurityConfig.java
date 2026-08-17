package com.minsuke.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.minsuke.auth.security.MinsukeUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final MinsukeUserDetailsService userDetailsService;

    public SecurityConfig(MinsukeUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/css/**", "/health").permitAll()
                        .requestMatchers("/events/new", "/events/*/edit").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/events").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/events/*/edit").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/events/*/attend").hasRole("PARENT")
                        .requestMatchers(HttpMethod.POST, "/families/*/delete").hasRole("ADMIN")
                        .requestMatchers("/instructors/new", "/instructors/*/edit").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/instructors/*/deactivate").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/instructors/*/delete").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/instructors/new").hasRole("ADMIN")
                        .requestMatchers("/announcements/new", "/announcements/*/edit").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/announcements/new").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/announcements/*/edit").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/announcements/*/delete").hasRole("ADMIN")
                        .requestMatchers("/schedules/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/my-family/**", "/my-participations").hasRole("PARENT")
                        .anyRequest().authenticated())
                .userDetailsService(userDetailsService)
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/calendar", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/error/forbidden"));
        return http.build();
    }
}
