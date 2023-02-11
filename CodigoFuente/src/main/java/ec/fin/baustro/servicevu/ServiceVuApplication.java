package ec.fin.baustro.servicevu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@Configuration
@EnableResourceServer
@EnableFeignClients
public class ServiceVuApplication {

	public static void main(String[] args) {

		SpringApplication.run(ServiceVuApplication.class, args);
	}

}
