package ec.fin.baustro.servicevu.service.registrocivil;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Banco del Austro
 * Interfaz para consumir el microservicio del Registro Civil
 */
@FeignClient(url="${configuration.url.registro.civil}", name = "IConsumeRegistroCivil")
public interface IConsumeRegistroCivil {
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    String consumeRegistroCivil(@RequestBody String body);
}
