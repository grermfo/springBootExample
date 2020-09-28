package com.grermfo.springBootExample.config.auth;

import com.grermfo.springBootExample.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserTypesOAuth2UserService customAuth2UserService;

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers("/","/css/**","/images/**","/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/s1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
            .and()
                .logout()
                .logoutSuccessUrl("/")
            .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customAuth2UserService);


    }
}
