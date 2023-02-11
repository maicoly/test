package ec.fin.baustro.servicevu.service.registrocivil;

import com.google.gson.Gson;
import ec.fin.baustro.servicevu.model.InputCitizen;
import ec.fin.baustro.servicevu.model.RegistroCivil;
import ec.fin.baustro.servicevu.security.EncryptDecrypt256;
import ec.fin.baustro.servicevu.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Banco del Austro
 * Servicio que devuelve la informacion y foto de un ciudadano por medio del Registro Civil
 */
@Service("RegistroCivilService")
public class RegistroCivilService implements IRegistroCivilService {

    @Autowired
    private IConsumeRegistroCivil iConsumeRegistroCivil;
    private ThreadLocal<Long> uuid = new ThreadLocal<>();
    @Value("${encryption.seed.registro_civil}")
    private String seedRegistroCivil;
    @Value("${encryption.active.registro_civil}")
    private boolean encryptRegistroCivil;
    private Gson gson = new Gson();

    /**
     * Devuelve la informacion de un ciudadano por medio del numero de cedula y dactilar
     *
     * @param input parametros de la trama enviada
     * @return String respuesta con toda la informacion del ciudadano
     */
    @Override
    public String getCitizenInformation(String input, String ip) {
        // * Generar un uuid
        uuid.set(new TransactionUtil().genuidLog());
        Log.printLogs("info", uuid.get(), String.format("Ingresa peticion para obtener la informacion de la persona (IP: %s): %s", ip.replaceAll("[\r\n]", ""), input.replaceAll("[\r\n]", "")));

        // * Convertir input de tipo String a InputCitizen para trabajar con los datos de la trama
        InputCitizen inputCitizen = gson.fromJson(input, InputCitizen.class);
        String responseCitizenInformation;

        if (inputCitizen.getNumber() != null && inputCitizen.getData().get("dactilar") != null) {
            // * Enviar trama al micro de Registro Civil
            RegistroCivil registroCivil = sendRequestRegistroCivil(inputCitizen, ip);
            Log.printLogs("info", uuid.get(), String.format("Respuesta Registro Civil (IP: %s): %s", ip.replaceAll("[\r\n]", ""), registroCivil.toString().replaceAll("[\r\n]", "")));
            // * Estructurar la respuesta del Registro Civil con lo requerido por VU
            StructureResponseRegistroCivil structureResponseRegistroCivil = new StructureResponseRegistroCivil();
            responseCitizenInformation = structureResponseRegistroCivil.structureCitizenInformation(registroCivil, uuid.get());
            Log.printLogs("info", uuid.get(), String.format("Respuesta (IP: %s): %s", ip.replaceAll("[\r\n]", ""), responseCitizenInformation.replaceAll("[\r\n]", "")));
        } else {
            responseCitizenInformation = ManageError.errorMessage(inputCitizen.getNumber(), uuid.get());
        }
        uuid.remove();
        return responseCitizenInformation.replace("%2F", "/");
    }

    /**
     * Devuelve la fotografia de la cedula del ciudadano por medio del numero de cedula y dactilar
     *
     * @param input parametros de la trama enviada
     * @return ResponseEntity<Object> respuesta con la fotografia del ciudadano
     */
    @Override
    public ResponseEntity<Object> getCitizenBiometrics(String input, String ip) {
        // * Generar un uuid
        uuid.set(new TransactionUtil().genuidLog());
        Log.printLogs("info", uuid.get(), String.format("Ingresa peticion para obtener la fotografia de la cedula de la persona (IP: %s): %s", ip.replaceAll("[\r\n]", ""), input.replaceAll("[\r\n]", "")));

        // * Convertir input de tipo String a InputCitizen para trabajar con los datos de la trama
        InputCitizen inputCitizen = gson.fromJson(input, InputCitizen.class);
        MultiValueMap<String, Object> response = new LinkedMultiValueMap<String, Object>();

        if (inputCitizen.getNumber() != null && inputCitizen.getData().get("dactilar").toString() != null) {
            // * Enviar trama al micro de Registro Civil
            RegistroCivil registroCivil = sendRequestRegistroCivil(inputCitizen, ip);
            Log.printLogs("info", uuid.get(), String.format("Respuesta Registro Civil (IP: %s): %s", ip.replaceAll("[\r\n]", ""),registroCivil.toString().replaceAll("[\r\n]", "")));
            // * Estructurar la respuesta del Registro Civil con lo requerido por VU
            response = StructureResponseRegistroCivil.structureCitizenBiometrics(registroCivil, uuid.get());
            Log.printLogs("info", uuid.get(), String.format("Respuesta (IP: %s): %s", ip.replaceAll("[\r\n]", ""),response.toString().replaceAll("[\r\n]", "")));
            uuid.remove();
            return codeResponseBiometrics(response);
        } else {
            String messageError = ManageError.errorMessage(inputCitizen.getNumber(), uuid.get());
            response.add("json", gson.fromJson(messageError, JsonObjError.class));
            uuid.remove();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Devuelve la trama para consultar al microservicio del Registro Civil
     *
     * @param nui      cedula del ciudadano
     * @param dactilar dactilar con los 6 primeros digitos
     * @return String trama para consultar al Registro Civil
     */
    public String sendInputRegistroCivil(String nui, String dactilar, String ip) {
        // * Trama para enviar al micro de Registro Civil
        RegistroCivil inputRegistroCivil = BodyUtils.bodyRegistroCivil(nui, dactilar, ip);
        String jsonInput = gson.toJson(inputRegistroCivil);
        // *  Encriptar o no la trama dependiendo si la encriptacion en el registro civil esta subida
        if (encryptRegistroCivil) {
            jsonInput = EncryptDecrypt256.encryptAES(jsonInput, seedRegistroCivil);
        }

        Log.printLogs("info", uuid.get(), String.format("TRAMA DE CONSULTA REGISTRO CIVIL (IP: %s)",ip));
        Log.printLogs("info", uuid.get(), "####### " + jsonInput + " #######");
        return jsonInput;
    }

    /**
     * Devuelve los 6 primeros digitos del dactilar
     *
     * @param dactilar dactilar de la trama enviada
     * @return String dactilar recortado
     */
    public String cutDactilar(String dactilar) {
        // * Se debe cortar a los 6 primeros digitos del dactilar enviado en la trama
        if (dactilar.length() > 6) {
            return dactilar.substring(0, 6);
        } else {
            return dactilar;
        }
    }

    public RegistroCivil sendRequestRegistroCivil(InputCitizen inputCitizen, String ip) {
        // * Tomar los 6 primeros digitos del dactilar enviado en la trama
        String dactilarSixDigits = cutDactilar(inputCitizen.getData().get("dactilar").toString());
        // * Enviar trama al microservicio de Registro Civil
        String responseRegistroCivil = iConsumeRegistroCivil.consumeRegistroCivil(sendInputRegistroCivil(inputCitizen.getNumber(), dactilarSixDigits, ip));
        // *  Desencriptar la respuesta del micro del Registro Civil
        if (encryptRegistroCivil){
            responseRegistroCivil = EncryptDecrypt256.decryptAES(responseRegistroCivil.getBytes(), seedRegistroCivil);
        }
        return gson.fromJson(responseRegistroCivil, RegistroCivil.class);
    }

    public ResponseEntity codeResponseBiometrics(MultiValueMap<String, Object> response){
        if (response.size() == 2) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
