package ec.fin.baustro.servicevu.utils;

import java.util.UUID;

/**
 * @author Banco del Austro
 * Clase que genera un uid unico para logs en el backend
 */
public class TransactionUtil {

    public TransactionUtil(){}

    /**
     * Método para generar un UID para algunas transacciones que lo requieren.
     * **/
    public long genuidLog() {
        return UUID.randomUUID().hashCode() & Long.MAX_VALUE;
    }

}