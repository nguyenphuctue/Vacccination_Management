package com.training.configure;

import com.training.serviceImpl.CustomerDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.training.serviceImpl.EmployeeDetailsServiceImpl;
import com.training.utils.Constant;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final EmployeeDetailsServiceImpl employeeDetailsService;
    private final CustomerDetailsServiceImpl customerDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(employeeDetailsService).passwordEncoder(passwordEncoder);
        auth.userDetailsService(customerDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
        .authorizeRequests()
        .antMatchers(Constant.RESOURCES_FOLDER).permitAll()
        .antMatchers(Constant.ADMIN_ROLES).access("hasRole('ROLE_ADMIN')")
        .antMatchers(Constant.USER_ROLES).access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
        .antMatchers("/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        	.loginPage("/login")
        	.defaultSuccessUrl("/home", true)
        	.failureUrl("/login?error")
        	.and()
        .logout()
        	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        	.logoutSuccessUrl("/login?logout")
        	.and()
        .exceptionHandling()
			.accessDeniedPage("/403");
    }
}
