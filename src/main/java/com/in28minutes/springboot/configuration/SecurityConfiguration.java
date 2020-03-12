package com.in28minutes.springboot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1")
                .password("{noop}secret1").roles("USER").and()
                .withUser("admin1")
                .password("{noop}secret1").roles("USER", "ADMIN");
        //{noop} specifies the identifier of PasswordEncoder
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers("/surveys/**").hasRole("USER")
                .antMatchers("/users/**").hasRole("USER")
                .antMatchers("/**").hasRole("ADMIN")
                .and().csrf().disable()
                .headers().frameOptions().disable();
    }
}