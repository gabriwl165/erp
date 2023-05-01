package br.com.gab.Sistema.ERP.Controller;

import br.com.gab.Sistema.ERP.Security.JwtAuthenticationFilter;
import br.com.gab.Sistema.ERP.Security.JwtAuthorizationFilter;
import br.com.gab.Sistema.ERP.Service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CorsConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManagerBean());
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login/**").permitAll();
        http.addFilter(jwtAuthenticationFilter);
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//                .authorizeRequests()
//                .antMatchers("/usuario/*").hasRole("ADMIN").anyRequest().authenticated()

    }


//    @Bean
//    protected UserDetailsManager userDetailsService(DataSource dataSource) {
//        User.UserBuilder users = User.withDefaultPasswordEncoder();
//        UserDetails annaSmithUser = users
//                .username("annasmith")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        UserDetails lindaUser = users
//                .username("linda")
//                .password("password")
//                .roles("ADMIN")
//                .build();
//
//        JdbcUserDetailsManager usersReturn = new JdbcUserDetailsManager(dataSource);
//        usersReturn.createUser(lindaUser);
//        usersReturn.createUser(annaSmithUser);
//        return usersReturn;
////        return new InMemoryUserDetailsManager(annaSmithUser, lindaUser);
//
//    }


}
