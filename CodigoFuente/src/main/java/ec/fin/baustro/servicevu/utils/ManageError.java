package ec.fin.baustro.servicevu.utils;

import com.google.gson.Gson;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Banco del Austro
 * Clase que maneja los errores al obtener la informacion del ciudadano o la fotografia de la cedula
 */
public class ManageError {

    private ManageError() {
        throw new IllegalStateException("Clase manejo de errores informacion usuario");
    }

    /**
     * Devuelve la informacion de un ciudadano por medio del numero de cedula y dactilar
     * @param nui cedula del ciudadano
     * @param uuid uuid para continuar con la secuencia
     * @return String respuesta en base al tipo de error
     */
    public static String errorMessage(String nui, Long uuid) {
        String messageResponse = "";
        try {
            messageResponse = FileHelper.readFile("vuRegistroCivil/ResponseError.txt");
            messageResponse = messageResponse.replace("codeResponse", "1000");
            messageResponse = messageResponse.replace("messageResponseResponse", "Parametros de entrada incorrectos");
        } catch (Exception e) {
            Log.printLogs("erro", uuid, String.format("ERROR: %s", e.getMessage().replaceAll("[\r\n]", "")));
        }
        if (nui == null) {
            Log.printLogs("erro", uuid, "ERROR: el parametro nui no puede estar vacío");
            messageResponse = messageResponse.replace("reasonResponse", "el parametro nui no puede estar vacío");
        } else {
            Log.printLogs("erro", uuid, "ERROR: el parametro dactilar no puede estar vacío");
            messageResponse = messageResponse.replace("reasonResponse", "el parametro dactilar no puede estar vacío");
        }
        return messageResponse;
    }

    public static String errorMessageCitizenInformation(Long uuid) {
        String messageResponse = "";
        try {
            messageResponse = FileHelper.readFile("vuRegistroCivil/ResponseError.txt");
            messageResponse = messageResponse.replace("codeResponse", "301");
            messageResponse = messageResponse.replace("messageResponseResponse", "Error en la informacion del ciudadano en el registro civil");
            messageResponse = messageResponse.replace("reasonResponse", "Error en la obtencion de la informacion del ciudadano. Ningún ciudadano encontrado con este NUI y Dactilar");
        } catch (Exception e) {
            Log.printLogs("erro", uuid, String.format("Error: %s", e.getMessage().replaceAll("[\r\n]", "")));
        }
        return messageResponse;
    }

    public static MultiValueMap<String, Object> errorMessageCitizenBiometrics(Long uuid) {
        try {
            String messageResponse = FileHelper.readFile("static/ResponseError.txt");
            messageResponse = messageResponse.replace("codeResponse", "321");
            messageResponse = messageResponse.replace("messageResponseResponse", "Error en la foto de la cedula del ciudadano");
            messageResponse = messageResponse.replace("reasonResponse", "Error en la obtencion de la foto de la cedula del ciudadano. Ningún ciudadano encontrado con este NUI y Dactilar");

            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
            Gson g = new Gson();
            formData.add("json", g.fromJson(messageResponse, JsonObjError.class));
            return formData;
        } catch (Exception e) {
            Log.printLogs("erro", uuid, String.format("Error: %s", e.getMessage().replaceAll("[\r\n]", "")));
        }
        return null;
    }
}
