package ec.fin.baustro.servicevu.utils;

import ec.fin.baustro.servicevu.model.RegistroCivil;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Banco del Austro
 * Clase para construir la trama para consultar al Registro Civil
 */
public class BodyUtils {

    /**
     * Devuelve la informacion de un ciudadano por medio del numero de cedula y dactilar
     * @param nui numero de cedula de la persona
     * @param dactilar dactilar de la persona
     * @param ip ip del dispositivo
     * @return RegistroCivil respuesta con toda la informacion obtenida del ciudadano en el Registro Civil
     */
    public static RegistroCivil bodyRegistroCivil(String nui, String dactilar, String ip) {
        RegistroCivil inputRegistroCivil = new RegistroCivil();
        inputRegistroCivil.setUsr("USRAPP");
        inputRegistroCivil.setCan("MBL");
        Map<String, Object> ctl = new HashMap<>();
        ctl.put("nui", nui);
        ctl.put("dactilar", dactilar);
        ctl.put("ip", ip);
        inputRegistroCivil.setCtl(ctl);
        return inputRegistroCivil;
    }
}
