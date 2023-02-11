package ec.fin.baustro.servicevu.service;

import ec.fin.baustro.servicevu.security.EncryptDecrypt256;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service("CommandOnboardingPublicPrivate")
public class CommandOnboardingPublicPrivate implements CommandService {
    @Value("${vu.apikey_public}")
    private String apiKeyPublic;
    @Value("${vu.apikey_private}")
    private String apiKeyPrivate;
    @Value("${vu.uri}")
    private String uri;
    @Value("${encrypt.seed.onboarding.vu}")
    private String seedAPI;

    @Override
    public String consumeService(String body, String urlMethod, String headerName) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        if (headerName.equals("x-access-apikey")) {
            headers.add(headerName, EncryptDecrypt256.decryptAES(apiKeyPublic.getBytes(), seedAPI));
        } else {
            headers.add(headerName, EncryptDecrypt256.decryptAES(apiKeyPrivate.getBytes(), seedAPI));
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate onboardinRest = new RestTemplate();
        HttpEntity<String> entityOnboarding = new HttpEntity<>(body, headers);
        return onboardinRest.postForObject(uri + urlMethod, entityOnboarding, String.class);
    }
}
