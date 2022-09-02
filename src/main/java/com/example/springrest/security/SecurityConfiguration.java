package com.example.springrest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((httpz) ->
                /*не получается ограничить для второго юзера методы POST, PUT и DELETE*/
                httpz.antMatchers("/users*/*").hasAnyRole("FULL_ACCESS", "INFO_ACCESS")
                        .antMatchers(HttpMethod.GET,"/users*/*").hasRole("INFO_ACCESS")
        ).httpBasic();

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager detailsManager() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("123")
                .roles("FULL_ACCESS")
                .build();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("321")
                .roles("INFO_ACCESS")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }
}
