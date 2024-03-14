package br.api.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                .oauth2Login(oauth2 -> oauth2
		    .loginPage("/login/oauth2")
		    .authorizationEndpoint(authorization -> authorization
                        .baseUri("/login/oauth2/authorization")
                    )
                    .redirectionEndpoint(redirection -> redirection
			.baseUri("/login/oauth2/callback/*")
                    )
		);
            return http.build();
        }
}
