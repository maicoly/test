package ec.fin.baustro.servicevu.model;

import java.util.HashMap;
import java.util.Map;

public class InputCitizen {
    private String number;
    private Map<String, Object> data = new HashMap<>();

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
