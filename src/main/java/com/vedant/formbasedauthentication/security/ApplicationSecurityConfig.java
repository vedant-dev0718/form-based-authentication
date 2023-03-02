package com.vedant.formbasedauthentication.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.vedant.formbasedauthentication.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;

    private static final String[] AUTH_WHITE_LIST = {
            "/",
            "index",
            "/css/*",
            "/js/*"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(AUTH_WHITE_LIST).permitAll()
                .requestMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/courses", true)
                .and()
                .rememberMe();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails vedantUser = User.builder()
                .username("vedant")
                .password(passwordEncoder.encode("vedant"))
                .authorities(STUDENT.getGrantedAuthorities())
                .build();
        UserDetails lindaUser = User.builder()
                .username("linda")
                .password(passwordEncoder.encode("linda333"))
                .authorities(ADMIN.name())
                //.roles(STUDENT.name()) // ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails tomUser = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("tom555"))
                .authorities(ADMINTRAINEE.name())
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                //      .roles(ADMINTRAINEE.name()) // ROLE ADMINTRAINEE
                .build();

        UserDetails tolik = User.builder()
                .username("tolik")
                .password(passwordEncoder.encode("tolik1"))
                .authorities(STUDENT.name())
                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        UserDetails hotamboyUser = User.builder()
                .username("hotam")
                .password(passwordEncoder.encode("hotamboy"))
                .authorities(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                //    .roles(ADMIN.name()) // ROLE ADMIN
                .build();

        return new InMemoryUserDetailsManager(
                vedantUser,
                lindaUser,
                tomUser,
                hotamboyUser,
                tolik
        );
    }
}
