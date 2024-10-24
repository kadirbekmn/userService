package com.moonlight.userservice.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF'yi devre dışı bırak
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register").permitAll()  // Kayıt endpoint'i serbest
                        .anyRequest().authenticated()  // Diğer tüm istekler kimlik doğrulama ister
                )
                .httpBasic(withDefaults());  // httpBasic() kimlik doğrulama


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Şifreleri hashlemek için BCrypt kullanacağız
    }
}
