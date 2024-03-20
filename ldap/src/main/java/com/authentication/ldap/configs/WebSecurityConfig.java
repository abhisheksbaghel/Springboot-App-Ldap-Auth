package com.authentication.ldap.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Value("${ldap.urls}")
    private String ldapUrls;

    @Value("${ldap.base.dn}")
    private String ldapBaseDn;

    @Value("${ldap.username}")
    private String securityPrincipal;

    @Value("${ldap.password}")
    private String ldapPrincipalPassword;

    @Value("${ldap.user.dn.pattern}")
    private String ldapUserDnPattern;

    @Value("${ldap.enabled}")
    private String ldapEnabled;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests((authorize)-> authorize.anyRequest().fullyAuthenticated())
                .formLogin(Customizer.withDefaults())
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.clearAuthentication(true));
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.ldapAuthentication()
                .contextSource()
                .url(ldapUrls+ldapBaseDn)
                .managerDn(securityPrincipal)
                .managerPassword(ldapPrincipalPassword)
                .and()
                .userDnPatterns(ldapUserDnPattern);
    }

//    @Autowired
//    public void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth
//                .ldapAuthentication()
//                .userDnPatterns("uid={0},ou=people")
//                .groupSearchBase("ou=groups")
//                .contextSource()
//                .url("ldap://host:port/dc=example,dc=com")
//                .and()
//                .passwordCompare()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .passwordAttribute("userPassword");
//    }

//    @Autowired
//    public  void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
//        authenticationManagerBuilder
//                .ldapAuthentication()
//                .userSearchFilter("uid={0}")
//                .userSearchBase("dc=example,dc=com")
//                .groupSearchFilter("uniqueMember={0}")
//                .groupSearchBase("ou=mathematicians,dc=example,dc=com")
//                .userDnPatterns("uid={0}")
//                .contextSource()
//                .url("ldap://ldap.forumsys.com:389")
//                .managerDn("cn=read-only-admin,dc=example,dc=com")
//                .managerPassword("password");
//    }
}
