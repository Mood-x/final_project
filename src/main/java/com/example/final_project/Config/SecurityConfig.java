package com.example.final_project.Config;

import com.example.final_project.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/user/register",
                        "/api/v1/center/center-register",
                        "/api/v1/parent/register").permitAll()
                .requestMatchers(
                        "/api/v1/parent/update",
                        "/api/v1/comments/add",
                        "/api/v1/complaint/add",
                        "/api/v1/child/register",
                        "/api/v1/child/update/{id}",
                        "/api/v1/child/update/{id}",
                        "/api/v1/comments/delete/{id}").hasAuthority("PARENT") // PARENT
                .requestMatchers(
                        "/api/v1/center/update-center/**",
                        "api/v1/center/add-program" ,
                        "api/v1/center/get-my-programs",
                        "/api/v1/comments/get-all",
                        "/api/v1/complaint/get-all").hasAuthority("CENTER") // CENTER
                .requestMatchers(
                        "/api/v1/user/get-all-users",
                        "/api/v1/parent/get-all",
                        "/api/v1/parent/delete/{id}",
                        "/api/v1/center/delete-center/{centerid}",
                        "/api/v1/center/get-all-centers").hasAuthority("ADMIN") // ADMIN

                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}
