package ec.fin.baustro.servicevu.utils;

import com.google.gson.Gson;
import ec.fin.baustro.servicevu.model.CitizenInformation;
import ec.fin.baustro.servicevu.model.JsonObjBiometrics;
import ec.fin.baustro.servicevu.model.RegistroCivil;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.xml.bind.DatatypeConverter;

/**
 * @author Banco del Austro
 * Clase para devolver en el formato establecido por VU las respuestas del Registro Civil
 */
public class StructureResponseRegistroCivil {

    /**
     * Devuelve la informacion de un ciudadano con la estructura requerida por VU
     *
     * @param responseRegistroCivil respuesta del Registro Civil
     * @param uuid                  uuid para continuar con la secuencia
     * @return String respuesta la informacion del Registro Civill estructurada
     */
    public String structureCitizenInformation(RegistroCivil responseRegistroCivil, Long uuid) {

        String codeResponse = responseRegistroCivil.getCodRespuesta();
        if (codeResponse.equals("000")) {
            Log.printLogs("info", uuid, "###### CONSULTA INFORMACION CIUDADANO REALIZADA CORRECTAMENTE EN EL REGISTRO CIVIL ######");

            CitizenInformation citizenInformation = new CitizenInformation();
            getPersonalName(responseRegistroCivil, citizenInformation);
            getBirthPlaceInformation(responseRegistroCivil, citizenInformation);
            getBirthDateInformation(responseRegistroCivil, citizenInformation);
            getAditionalInformation(responseRegistroCivil, citizenInformation);
            return getStructureInformacionVU(citizenInformation, uuid);
        } else {
            Log.printLogs("erro", uuid, "###### ERROR EN LA CONSULTA INFORMACION CIUDADANO EN EL REGISTRO CIVIL ######");
            return ManageError.errorMessageCitizenInformation(uuid);
        }
    }

    /**
     * Devuelve la informacion de un ciudadano con la estructura requerida por VU
     * @param responseRegistroCivil respuesta del Registro Civil
     * @param uuid                  uuid para continuar con la secuencia
     * @return String respuesta la informacion del Registro Civill estructurada
     */
    public static MultiValueMap<String, Object> structureCitizenBiometrics(RegistroCivil responseRegistroCivil, Long uuid) {
        String codeResponse = responseRegistroCivil.getCodRespuesta();

        if (codeResponse.equals("000")) {
            Log.printLogs("info", uuid, "###### CONSULTA FOTOGRAFIA CEDULA REALIZADA CORRECTAMENTE EN EL REGISTRO CIVIL ######");
            String photoResponseBase64 = responseRegistroCivil.getCtl().get("fotografia").toString();

            byte[] result = DatatypeConverter.parseBase64Binary(photoResponseBase64);
            try {
                String message = FileHelper.readFile("vuRegistroCivil/ResponseCitizenBiometrics.txt");
                message = message.replace("codeResponse", "320");
                message = message.replace("messageResponse", "Foto de la cedula del ciudadano");

                MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
                Gson gson = new Gson();
                formData.add("json", gson.fromJson(message, JsonObjBiometrics.class));
                formData.add("file", new ByteArrayResource(result));
                return formData;
            } catch (Exception e) {
                Log.printLogs("erro", uuid, String.format("Error: %s", e.getMessage().replaceAll("[\r\n]", "")));
            }
        } else {
            return ManageError.errorMessageCitizenBiometrics(uuid);
        }
        return new LinkedMultiValueMap<String, Object>();
    }

    public void getPersonalName(RegistroCivil responseRegistroCivil, CitizenInformation citizenInformation) {
        citizenInformation.setFullName(responseRegistroCivil.getCtl().get("nombre").toString());
        if (!citizenInformation.getFullName().isEmpty()) {
            String[] fullNameSplit = citizenInformation.getFullName().split(" ");
            switch (fullNameSplit.length){
                case 4:
                    citizenInformation.setNames(fullNameSplit[0] + " " + fullNameSplit[1]);
                    citizenInformation.setLastNames(fullNameSplit[2] + " " + fullNameSplit[3]);
                    break;
                case 3:
                    citizenInformation.setNames(fullNameSplit[0]);
                    citizenInformation.setLastNames(fullNameSplit[1] + " " + fullNameSplit[2]);
                    break;
                case 2:
                    citizenInformation.setNames(fullNameSplit[0]);
                    citizenInformation.setLastNames(fullNameSplit[1]);
                    break;
            }
        }
    }

    public void getBirthDateInformation(RegistroCivil responseRegistroCivil, CitizenInformation citizenInformation){
        if (!responseRegistroCivil.getCtl().get("fechaNacimiento").toString().equals("")) {
            String[] birthDateSplit = responseRegistroCivil.getCtl().get("fechaNacimiento").toString().split("/");
            citizenInformation.setBirthDate(birthDateSplit[2] + birthDateSplit[1] + birthDateSplit[0]);
        } else {
            citizenInformation.setNames(null);
            citizenInformation.setLastNames(null);
        }
    }

    public void getBirthPlaceInformation(RegistroCivil responseRegistroCivil, CitizenInformation citizenInformation){
        if (!responseRegistroCivil.getCtl().get("lugarNacimiento").toString().equals("")) {
            String[] birthPlaceSplit = responseRegistroCivil.getCtl().get("lugarNacimiento").toString().split("/");
            switch (birthPlaceSplit.length){
                case 3:
                    citizenInformation.setBirthPlace(birthPlaceSplit[2].split(" ")[0]);
                    citizenInformation.setBirthProvince(birthPlaceSplit[0]);
                    break;
                case 2:
                    citizenInformation.setBirthPlace(birthPlaceSplit[1]);
                    citizenInformation.setBirthProvince(birthPlaceSplit[0]);
                    break;
                case 1:
                    citizenInformation.setBirthPlace(birthPlaceSplit[0]);
                    citizenInformation.setBirthProvince(birthPlaceSplit[0]);
                    break;
            }
        }
    }

    public void getAditionalInformation(RegistroCivil responseRegistroCivil, CitizenInformation citizenInformation){
        citizenInformation.setNui(responseRegistroCivil.getCtl().get("nui").toString());
        citizenInformation.setNationality(responseRegistroCivil.getCtl().get("nacionalidad").toString());
        citizenInformation.setProfession(responseRegistroCivil.getCtl().get("profesion").toString());
        if (responseRegistroCivil.getCtl().get("sexo").toString().equals("HOMBRE")) {
            citizenInformation.setGender("M");
        } else if (responseRegistroCivil.getCtl().get("sexo").toString().equals("MUJER")) {
            citizenInformation.setGender("F");
        }
        citizenInformation.setEducationLevel(responseRegistroCivil.getCtl().get("instruccion").toString());
        citizenInformation.setMotherName(responseRegistroCivil.getCtl().get("nombreMama").toString());
        citizenInformation.setFatherName(responseRegistroCivil.getCtl().get("nombrePapa").toString());
        citizenInformation.setAddress(responseRegistroCivil.getCtl().get("calle").toString());
        citizenInformation.setCivilStatus(responseRegistroCivil.getCtl().get("estadoCivil").toString());
    }

    public String getStructureInformacionVU (CitizenInformation citizenInformation, Long uuid){
        try {
            String message = FileHelper.readFile("vuRegistroCivil/ResponseCitizenInformation.txt");
            message = message.replace("codeResponse", "300");
            message = message.replace("messageResponse", "Informacion del ciudadano");
            message = message.replace("namesResponse", citizenInformation.getNames());
            message = message.replace("lastNamesResponse", citizenInformation.getLastNames());
            message = message.replace("birthDateResponse", citizenInformation.getBirthDate());
            message = message.replace("numberResponse", citizenInformation.getNui());
            message = message.replace("nationalityResponse", citizenInformation.getNationality());
            message = message.replace("professionResponse", citizenInformation.getProfession());
            message = message.replace("fatherNameResponse", citizenInformation.getFatherName());
            message = message.replace("addressResponse", citizenInformation.getAddress());
            message = message.replace("genderResponse", citizenInformation.getGender());
            message = message.replace("birthPlaceResponse", citizenInformation.getBirthPlace());
            message = message.replace("educationLevelResponse", citizenInformation.getEducationLevel());
            message = message.replace("motherNameResponse", citizenInformation.getMotherName());
            message = message.replace("fullNameResponse", citizenInformation.getFullName());
            message = message.replace("birthProvinceResponse", citizenInformation.getBirthProvince());
            message = message.replace("civilStatusResponse", citizenInformation.getCivilStatus());
            return message;
        } catch (Exception e) {
            Log.printLogs("erro", uuid, String.format("Error: %s", e.getMessage().replaceAll("[\r\n]", "")));
        }
        return null;
    }
}
