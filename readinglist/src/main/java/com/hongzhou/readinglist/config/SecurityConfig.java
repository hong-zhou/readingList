package com.hongzhou.readinglist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hongzhou.readinglist.repository.ReaderRepository;

@Profile("prod")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ReaderRepository readerRepository;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/").access("hasRole('READER')")
				.antMatchers("/shutdown").access("hasRole('ADMIN')")
				.antMatchers("/**").permitAll()
			.and()
				.formLogin()
					.loginPage("/login")
					.failureUrl("/login?error=true");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailService())
			.and()
			.inMemoryAuthentication()
				.withUser("admin")
				.password("secrect")
				.roles("ADMIN", "READER");
	}

	@Bean
	private UserDetailsService userDetailService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				UserDetails userDetails = readerRepository.findOne(username);
				if (userDetails != null) {
					return userDetails;
				}
				throw new UsernameNotFoundException("User '" + username + "' not found.");
			}
		};
	}

}
