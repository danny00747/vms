package be.rentvehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class VmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(VmsApplication.class, args);
    }


    @Configuration
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        protected void configure(AuthenticationManagerBuilder auth) {
            //...
        }

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.httpBasic().and()
                    .authorizeRequests()
                    .antMatchers("/**")
                    .permitAll().anyRequest().authenticated()
                    .and().csrf().disable();
        }
    }
}
