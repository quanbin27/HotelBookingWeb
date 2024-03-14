package com.example.demo.config;

import com.example.demo.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
//    @Autowired
//    private DataSource dataSource;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
}
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        return providerManager;
    }

//    @Autowired
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }

//    @Bean
//public InMemoryUserDetailsManager userDetailsService() {
//    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    UserDetails user = User.withUsername("user1")
//            .password(encoder.encode("1234"))
//            .authorities("USER")
//            .build();
//    UserDetails admin = User.withUsername("admin1")
//            .password(encoder.encode("1234"))
//            .authorities("ADMIN")
//            .build();
//    return new InMemoryUserDetailsManager(user,admin);
//}
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(requests->requests
                    .requestMatchers("/","/signin","/logout","/home","rooms","/signup","/css/**","/img/**","/fonts/**","/js/**","/about","/contact","/booking").permitAll()
                    .requestMatchers("/admin").hasAuthority("ADMIN")

                    .requestMatchers("/userinfo").hasAnyAuthority("ADMIN","USER")
                    .anyRequest().authenticated()

                );
        http.formLogin().loginProcessingUrl("/j_spring_security_check").loginPage("/signin").successHandler((request, response, authentication) -> {
            Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
            boolean isAdmin=authorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
            if (isAdmin){
                response.sendRedirect("/admin");
            } else {
                response.sendRedirect("/home");
            }
        }).failureUrl("/signin?error=true").usernameParameter("username").passwordParameter("password").permitAll().and().logout() .logoutUrl("/logout") // Đường dẫn cho đăng xuất
                .logoutSuccessUrl("/home") // Đường dẫn sau khi đăng xuất thành công
                .invalidateHttpSession(true) // Hủy bỏ HTTP session sau khi đăng xuất
                .deleteCookies("JSESSIONID") // Xóa cookie JSESSIONID sau khi đăng xuất
                .permitAll().and().csrf().disable();
        return http.build();

    }



}
