package ec.fin.baustro.servicevu.service;

import ec.fin.baustro.servicevu.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class OnboardingProcess {

    private static final String SERVER_ERROR = "Server Error";
    public ResponseEntity<Object> onboardingEndpoints(String input, String endpoint, boolean pubicKEY, CommandOnboarding  commandOnboarding){
        String result;
        try {
            result = commandOnboarding.consumeService(input, endpoint, pubicKEY);
            log.info(String.format("Respuesta: %s -> %s", input.split(",")[0].replace("{", " ").replaceAll("[\r\n]", ""), result.replaceAll("[\r\n]", "")));
        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(ex.getResponseBodyAsString(), HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage().replaceAll("[\r\n]", ""));
            List<String> details = new ArrayList<>();
            details.add(ex.getLocalizedMessage());
            ErrorResponse error = new ErrorResponse(SERVER_ERROR, details);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
