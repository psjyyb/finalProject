package com.arion.app.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.arion.app.security.handler.CustomAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    // 1. 암호화/복호화에 사용하는 Bean 등록
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. 인증 및 인가
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	//csrf 설정
    	http.csrf().disable();
    	http.headers().frameOptions().disable();
    	
    	// 캐시 비활성화 설정
        //http.headers().cacheControl().disable();
        
        http.authorizeHttpRequests()
                .antMatchers( "/home", "/login", "/home/**", "/css/**", "/js/**", "/images/**", "/scss/**","/vendor/**", "/files/**", "/wsocket/**", "/qnaPw").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/groupAdmin/**").hasAnyRole("USER")
                .antMatchers("/pay/**").hasAnyRole("NO")
                .antMatchers("/group/**").hasAnyRole("임원", "일반직", "관리직")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .successHandler(customAuthenticationSuccessHandler) 
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
        	.logout()
        		.logoutUrl("/logout")
        		.logoutSuccessUrl("/login?logout=true")
        		.invalidateHttpSession(true) 
        		.clearAuthentication(true)
        		.permitAll();
        return http.build();
    }
    
    
}
