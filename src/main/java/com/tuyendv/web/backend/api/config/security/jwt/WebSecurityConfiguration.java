package com.tuyendv.web.backend.api.config.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * use 1 in 2: WebSecurityConfiguration or WebMvcConfig
 *
 * @author tuyendv custom
 * class for config web JWT Token
 **/

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    private static final String[] PUBLIC_URLS = {"/cmm/files/**", "swagger-ui/**", "/v3/api-docs/**", "/auth/login",
            "/c/register"};

    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        //set allowed url origins
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost"));
        //set allowed method
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT"));
        //set allowed header, CorsConfiguration.ALL = *
        configuration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        //allow sends credentials(cookies, headers,...) from client to server
        //if set true, allowedOrigins ALL is not allowed
        configuration.setAllowCredentials(true);
        //time browser save CORS preflight request
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //set CORS with url /**
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        //convert role in jwt -> use get role from token jwt, can use in SecurityFilterChain or OAuth2ResourceServer.
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(""); //set no prefix role

        //convert jwt -> authentication
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        //set granted author to authentication
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(PUBLIC_URLS).permitAll().anyRequest().authenticated())
                .headers(header -> header.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*")));

        //config oauth2 with jwt
        http.oauth2ResourceServer(
                oauth -> oauth.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder) //decoder toke -> object
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())) //jwt -> authentication
                        .authenticationEntryPoint(
                                new JwtAuthenticationEntryPoint())); //custom handle jwt validation error

        return http.build();
    }

}
