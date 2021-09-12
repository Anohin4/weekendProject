package com.example.weekendproject.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    final MyUserDetailService myUserDetailService;

    final
    DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String userQueries;

    @Value("${spring.queries.roles-query}")
    private String roleQueries;

    public SecurityConfig(MyUserDetailService myUserDetailService, DataSource dataSource) {
        this.myUserDetailService = myUserDetailService;
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(userQueries)
                .authoritiesByUsernameQuery(roleQueries);
        auth.userDetailsService(myUserDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()
                .authorizeRequests()
                .antMatchers("/homepage").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/addPost").hasRole("ADMIN")
                .antMatchers("/edit/**").hasRole("ADMIN")
                .antMatchers("/registration").anonymous()
                .antMatchers("/h2").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/homepage")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and().csrf().disable();
        http.headers().frameOptions().disable();


    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
