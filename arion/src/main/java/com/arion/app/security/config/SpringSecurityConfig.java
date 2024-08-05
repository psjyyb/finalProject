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
        http.authorizeHttpRequests()
                .antMatchers("/**", "/home", "/login", "/home/**", "/css/**", "/js/**", "/images/**", "/scss/**","/vendor/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/groupAdmin/**").hasAnyRole("USER")
                .antMatchers("/group/**").hasAnyRole("일반사원", "사원관리자")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .successHandler(customAuthenticationSuccessHandler) // 로그인 성공 시 처리 핸들러 설정
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();
//                .and()
//            .logout()
//                .logoutUrl("/perform_logout")
//                .logoutSuccessUrl("/")
//                .permitAll();
        return http.build();
    }
}
