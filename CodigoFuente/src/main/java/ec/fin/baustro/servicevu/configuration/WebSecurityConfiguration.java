package ec.fin.baustro.servicevu.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ws.config.annotation.EnableWs;

/**
 * @author Banco del Austro
 * Clase encargada de gestionar la autenticacion por medio de OAuth 2.0
 */
@EnableWebSecurity
@EnableWs
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${config.validate.token.oauth}")
    private boolean validateToken;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Se establece los paths que no tendran implementado OAuth 2.0
     *
     * @param webSecurity parametros de la trama enviada
     */
    @Override
    public void configure(WebSecurity webSecurity) {
        if (validateToken) {
            // * Endpoints los cuales no tendran autenticacion de oauth 2.0 si el validateToken es TRUE
            webSecurity
                    .ignoring()
                    .antMatchers("/onboarding/newOperation")
                    .antMatchers("/onboarding/addFront")
                    .antMatchers("/onboarding/addBack")
                    .antMatchers("/onboarding/register")
                    .antMatchers("/onboarding/endOperation")
                    .antMatchers("/vuConfigurations/selfieOnboardingActivity");
        } else {
            // * Endpoints los cuales no tendran autenticacion de oauth 2.0 si el validateToken es FALSE
            webSecurity
                    .ignoring()
                    .antMatchers("/onboarding/newOperation")
                    .antMatchers("/onboarding/addFront")
                    .antMatchers("/onboarding/addBack")
                    .antMatchers("/onboarding/register")
                    .antMatchers("/onboarding/endOperation")
                    .antMatchers("/biometrics/citizenBiometrics")
                    .antMatchers("/information/citizenInformation")
                    .antMatchers("/vuConfigurations/selfieOnboardingActivity");
        }
    }
}