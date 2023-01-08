package com.cybergyan.cybergyanelab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends AbstractHttpConfigurer<SecurityConfig, HttpSecurity> {

	@Autowired
	private UserDetailsService userDetailsService;

//	@Bean
//	public UserDetailsService userDetailsService() {
//	    return super.userDetailsService();
//	}

	public static SecurityConfig securityConfig() {
		return new SecurityConfig();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		http.addFilterBefore(authenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
			.antMatchers("/css/**", "/images/**","/js/**","/favicon.ico").permitAll()
			.antMatchers("/home")
			.hasAuthority("USER")
			.antMatchers("/admin")
			.hasAuthority("ADMIN")
			.anyRequest().authenticated()
			.and().formLogin()
				.loginPage("/login")
			.defaultSuccessUrl("/home")
			.failureUrl("/login?error=true")
        	.permitAll()
				.and()
				.apply(securityConfig());

		return http.getOrBuild();
	}

	//Raw password 
//	@Bean
//	AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider provider=
//				new DaoAuthenticationProvider();
//	provider.setUserDetailsService(userDetailsService);
//	provider.setPasswordEncoder(new PasswordEncoder() {
//		
//		@Override
//		public boolean matches(CharSequence rawPassword, String encodedPassword) {
//			// TODO Auto-generated method stub
//			return rawPassword.toString().equals(encodedPassword);
//		}
//		
//		@Override
//		public String encode(CharSequence rawPassword) {
//			// TODO Auto-generated method stub
//			 return rawPassword.toString();
//		}
//	});
//	return provider;
//	}
	
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	
	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
//		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/resources/**");
//
//	}

	public SimpleAuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager) throws Exception {
		SimpleAuthenticationFilter filter = new SimpleAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager);
		filter.setAuthenticationFailureHandler(failureHandler());
		return filter;
	}

	public SimpleUrlAuthenticationFailureHandler failureHandler() {
		return new SimpleUrlAuthenticationFailureHandler("/login?error=true");
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
	
}
