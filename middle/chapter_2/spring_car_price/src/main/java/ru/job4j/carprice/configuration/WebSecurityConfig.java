package ru.job4j.carprice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.job4j.carprice.persistence.repository.UserRepository;
import ru.job4j.carprice.service.UserService;

/**
 * Configuration of security.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserRepository repository;

    /**
     * Authorisation from USERS table
     * in database.
     * @param auth AuthenticationManagerBuilder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(new UserService(repository));
    }

    /**
     * Configure filter for authorisation.
     * @param http HttpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/login",
                        "/registration",
                        "/static/**",
                        "/api/login",
                        "/api/logout",
                        "/api/reg"
                )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage("/api/login").usernameParameter("login")
                .permitAll()
                .and()
                .csrf().disable();
    }
}
