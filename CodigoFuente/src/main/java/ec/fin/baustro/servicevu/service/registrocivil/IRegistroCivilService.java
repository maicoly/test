package ec.fin.baustro.servicevu.service.registrocivil;

import org.springframework.http.ResponseEntity;

public interface IRegistroCivilService {

    public String getCitizenInformation(String input, String ip);

    public ResponseEntity<Object> getCitizenBiometrics(String input, String ip);
}
