package ec.fin.baustro.servicevu.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Banco del Austro
 * Clase encargada de generar logs backend
 */
@Slf4j
public class Log {

    /**
     * Imprimir logs con formato
     * @param type
     * @param uuid
     * @param message
     */
    public static void printLogs(String type, Long uuid, String message) {
        switch (type) {
            case "erro":
                log.error(String.format("[%s] - %s", uuid, message));
                break;
            case "info":
            default:
                log.info(String.format("[%s] - %s", uuid, message));
                break;
        }
    }
}