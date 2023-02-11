package ec.fin.baustro.servicevu.utils;

import java.util.Map;

public class ProcessIn {
    public ProcessIn() {
    }
    public String returnControlCamp(Map<String, Object> ctl, String searchField) {
        if (ctl.get(searchField) != null) {
            String response = ctl.get(searchField).toString();
            return response;
        } else {
            return null;
        }
    }
}
