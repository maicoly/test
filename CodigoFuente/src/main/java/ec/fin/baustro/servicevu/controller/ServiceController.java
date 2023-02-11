package ec.fin.baustro.servicevu.controller;

import ec.fin.baustro.servicevu.service.CommandOnboarding;
import ec.fin.baustro.servicevu.service.OnboardingProcess;
import ec.fin.baustro.servicevu.utils.Functions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestScope
@RequestMapping("/onboarding")
public class ServiceController {
    private String ip = "";
    private final Functions functions = new Functions();

    @Autowired
    @Qualifier("commandOnboarding")
    private CommandOnboarding commandOnboarding;

    @PostMapping(value = "newOperation")
    public ResponseEntity<Object> newOperation(@RequestBody String input, HttpServletRequest request) throws ExecutionException, InterruptedException, UnknownHostException {
        ip = request.getRemoteAddr();
        log.info(String.format("Ingresa New Front (IP: %s) -> %s", ip.replaceAll("[\r\n]", ""), input.replaceAll("[\r\n]", "")));
        OnboardingProcess onboardingProcess = new OnboardingProcess();
        return onboardingProcess.onboardingEndpoints(input, "/onboarding/newOperation", true, commandOnboarding);
    }

    @PostMapping(value = "addFront")
    public ResponseEntity<Object> addFront(@RequestBody String input, HttpServletRequest request) throws ExecutionException, InterruptedException, UnknownHostException {
        ip = request.getRemoteAddr();
        String json = functions.replaceFileParameterVU(input, "file", "");
        log.info(String.format("Ingresa Add Front (IP: %s) -> %s", ip.replaceAll("[\r\n]", ""), json.replaceAll("[\r\n]", "")));
        OnboardingProcess onboardingProcess = new OnboardingProcess();
        return onboardingProcess.onboardingEndpoints(input, "/onboarding/addFront", true, commandOnboarding);
    }

    @PostMapping(value = "addBack")
    public ResponseEntity<Object> addBack(@RequestBody String input, HttpServletRequest request) throws ExecutionException, InterruptedException, UnknownHostException {
        ip = request.getRemoteAddr();
        String json = functions.replaceFileParameterVU(input, "file", "");
        log.info(String.format("Ingresa Add Back (IP: %s) -> %s", ip.replaceAll("[\r\n]", ""), json.replaceAll("[\r\n]", "")));
        OnboardingProcess onboardingProcess = new OnboardingProcess();
        return onboardingProcess.onboardingEndpoints(input, "/onboarding/addBack", true, commandOnboarding);
    }

    @PostMapping(value = "register")
    public ResponseEntity<Object> register(@RequestBody String input, HttpServletRequest request) throws ExecutionException, InterruptedException, UnknownHostException {
        ip = request.getRemoteAddr();
        String json = functions.replaceFileParameterVU(input, "analysisSelfieList", "selfieList");
        log.info(String.format("Ingresa Register (IP: %s) -> %s", ip.replaceAll("[\r\n]", ""), json.replaceAll("[\r\n]", "")));
        OnboardingProcess onboardingProcess = new OnboardingProcess();
        return onboardingProcess.onboardingEndpoints(input, "/onboarding/register", true, commandOnboarding);
    }

    @PostMapping(value = "endOperation")
    public ResponseEntity<Object> endOperation(@RequestBody String input, HttpServletRequest request) throws ExecutionException, InterruptedException, UnknownHostException {
        ip = request.getRemoteAddr();
        log.info(String.format("Ingresa End Operation (IP: %s) -> %s", ip.replaceAll("[\r\n]", ""), input.replaceAll("[\r\n]", "")));
        OnboardingProcess onboardingProcess = new OnboardingProcess();
        return onboardingProcess.onboardingEndpoints(input, "/onboarding/endOperation", false, commandOnboarding);
    }
}
