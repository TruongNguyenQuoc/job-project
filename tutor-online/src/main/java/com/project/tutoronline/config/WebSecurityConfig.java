package com.project.tutoronline.config;

import com.project.tutoronline.handler.LoginFailureHandler;
import com.project.tutoronline.handler.LoginSuccessHandler;
import com.project.tutoronline.service.CustomAccountDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginFailureHandler failureHandler;

    @Autowired
    private LoginSuccessHandler successHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomAccountDetailService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        http.csrf().disable().cors().configurationSource(request -> corsConfiguration);

        http.authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/","/sendmail", "/front/post/**", "/front/register/**", "/login/**", "/register/**", "/forgot-password/**").permitAll()

                .antMatchers("/back/**").hasAnyAuthority("ADMIN")
                .antMatchers("/front/post/form/**").hasAnyAuthority( "PARENT")
                .antMatchers("/checkout-job/**").hasAnyAuthority( "TUTOR")
                .antMatchers("/front/**").hasAnyAuthority("TUTOR", "PARENT")

                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and().logout().permitAll()
                .and().exceptionHandling().accessDeniedPage("/error/403");
    }

}
