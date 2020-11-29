package com.project.demo.config;

import com.project.demo.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //autentykajca
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder((bCryptPasswordEncoder));

    }

    //autoryzacja
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("api/**")
                .hasRole("USER")
                .antMatchers("/", "/**").permitAll().anyRequest().authenticated();
        http.formLogin();
        http.httpBasic();
    }

    /*

     @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("SELECT username, password, enabled FROM users where username = ?")
//                .authoritiesByUsernameQuery("select username, authority from authorities where username = ?")
//                .passwordEncoder(new StandardPasswordEncoder("secret"));
//
//    }

    dane do logowania w inMemory:

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("Jan")
                .password("{noop}Sobieski") //{noop} - bez passwordEncoder
                .authorities("ROLE_USER")
                .and()
                .withUser("Cezar")
                .password("{noop}Brutus")
                .authorities("ROLE_USER");
    }

 */


}
