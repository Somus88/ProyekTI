package com.group3.healthconsult.middleware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.group3.healthconsult.core.MainUserDetailsService;

@Configuration
@EnableWebSecurity
public class AuthMiddleware {
    private MainUserDetailsService mainUserDetailsService;
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Autowired
    AuthMiddleware(MainUserDetailsService mainUserDetailsService, CustomLoginSuccessHandler customLoginSuccessHandler) {
        this.mainUserDetailsService = mainUserDetailsService;
        this.customLoginSuccessHandler = customLoginSuccessHandler;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        try {
            httpSecurity
                    .authorizeHttpRequests(requests -> requests
                            .requestMatchers("/consultations/new").authenticated()
                            .requestMatchers("/staff/**").authenticated()
                            .requestMatchers("/doctors/**").authenticated()
                            .requestMatchers("/", "/login", "/register", "/consultations/**").permitAll()
                            .anyRequest().permitAll())
                    .formLogin(form -> form
                            .loginPage("/login")
                            .successHandler(customLoginSuccessHandler)
                            .loginProcessingUrl("/login")
                            .failureUrl("/login?error=true")
                            .permitAll())
                    .logout(logout -> logout
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpSecurity.build();
    }

    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(mainUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
