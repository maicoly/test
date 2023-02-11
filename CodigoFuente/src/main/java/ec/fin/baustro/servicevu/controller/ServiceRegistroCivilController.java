package ec.fin.baustro.servicevu.controller;

import ec.fin.baustro.servicevu.service.registrocivil.RegistroCivilService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Banco del Austro
 * Controlador que posee dos paths para obtener la informacion y fotografica de la cedula de una persona por medio del registro civil
 */
@RestController
public class ServiceRegistroCivilController {

    private final RegistroCivilService registroCivilService;
    public ServiceRegistroCivilController(@Qualifier("RegistroCivilService") RegistroCivilService registroCivilService) {
        this.registroCivilService = registroCivilService;
    }

    /**
     * Devuelve la informacion de un ciudadano
     * @param input parametros de la trama enviada
     * @return String respuesta con toda la informacion del ciudadano
     */
    @PostMapping(value = "/information/citizenInformation", produces = MediaType.APPLICATION_JSON_VALUE)
    public String citizenInformation(@RequestBody String input, HttpServletRequest request) {
        String responseInformation = this.registroCivilService.getCitizenInformation(input, request.getRemoteAddr());
        return responseInformation;
    }

    /**
     * Devuelve la foto de la cedula de un ciudadano
     * @param input parametros de la trama enviada
     * @return ResponseEntity respuesta con toda la foto de la cedula del ciudadano
     */
    @PostMapping(value = "/biometrics/citizenBiometrics", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> citizenBiometrics(@RequestBody String input, HttpServletRequest request) {
        ResponseEntity<Object> responseBiometrics = this.registroCivilService.getCitizenBiometrics(input, request.getRemoteAddr());
        return responseBiometrics;
    }
}
