package ec.fin.baustro.servicevu.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author Banco del Austro
 * Clase que reemplaza las fotografias del proceso de VU para la escritura en logs
 */
public class Functions {

    /**
     * Devuelve las fotografias emplazadas por imagen recibida
     *
     * @param input parametros de la trama de VU
     * @return String json con las fotografias emplazadas por imagen recibida
     */
    public String replaceFileParameterVU(String input, String key, String key2) {
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(input, JsonObject.class);
        String[] keys = json.keySet().toArray(new String[0]);
        JsonElement element = gson.toJsonTree(json);
        return replacePhotos(keys, element, key2);
    }

    public String getLogResponse (String key, JsonElement element){
        Gson gson = new Gson();
        element.getAsJsonObject().remove(key);
        element.getAsJsonObject().addProperty("photo", "imagen recibida");
        return gson.toJson(element);
    }

    public String replacePhotos(String[] keys, JsonElement element, String key2){
        String newJsonParameter = "";
        int cont = 0;
        while (cont < keys.length) {
            newJsonParameter = replaceKey(keys, element, cont, key2);
            cont++;
        }
        return newJsonParameter;
    }

    public String replaceKey(String[] keys, JsonElement element, int cont, String key2){
        String newJsonParameter = "";
        if (keys[cont].equals("file") || keys[cont].equals(key2)) {
            newJsonParameter = getLogResponse("file", element);
            if (!key2.isEmpty()){
                newJsonParameter = getLogResponse(key2, element);
            }
        }
        return newJsonParameter;
    }
}
