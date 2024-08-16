package edu.poly.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .antMatchers("/site/order/**").authenticated()
                .antMatchers("/site/product", "/site/account/**", "/site/account/Register","/site/oauth2/login/success").permitAll()
                .antMatchers("/admin/**").hasAnyAuthority("DIRECTOR", "STAFF")
                .antMatchers("/site/**").hasAnyAuthority("ROLE_CUSTOMER", "STAFF", "DIRECTOR")
                
                
                .anyRequest().authenticated())
            .formLogin(formLogin -> formLogin
                .loginPage("/site/account/login").defaultSuccessUrl("/site/product", true).failureUrl("/site/account/login").permitAll())
            .logout(logout -> logout
                .logoutUrl("/site/logout")
                .logoutSuccessUrl("/site/account/login?logout").permitAll())
            .oauth2Login(oauth2Login -> oauth2Login
                .loginPage("/site/account/login")
                .defaultSuccessUrl("/site/oauth2/login/success", true)
                .failureUrl("/auth/login/error")
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization"));
        return http.build();
    }

}
