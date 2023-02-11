package ec.fin.baustro.servicevu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("commandOnboarding")
public class CommandOnboarding {
    @Autowired
    @Qualifier("CommandOnboardingPublicPrivate")
    private CommandOnboardingPublicPrivate commandOnboardingPublicPrivate ;
    public String consumeService(String body, String urlMethod, boolean pubicKEY) throws Exception {
        if(pubicKEY){
            return commandOnboardingPublicPrivate.consumeService(body,urlMethod, "x-access-apikey");
        }else {
            return commandOnboardingPublicPrivate.consumeService(body,urlMethod, "x-access-apikey-private");
        }
    }
}