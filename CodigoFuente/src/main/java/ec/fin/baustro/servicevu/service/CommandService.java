package ec.fin.baustro.servicevu.service;

public interface CommandService {
    String consumeService(String body, String urlMethod, String headerName) throws Exception;
}
