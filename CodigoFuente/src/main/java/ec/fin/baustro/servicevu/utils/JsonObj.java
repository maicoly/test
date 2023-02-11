package ec.fin.baustro.servicevu.utils;


import java.util.HashMap;
import java.util.Map;

public class JsonObj {
    private String number;
    private Map<String, Object> data = new HashMap();
    private String codRespuesta;
    private String msgUsuario;
    private String msgTecnico;

    public JsonObj() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCodRespuesta() {
        return codRespuesta;
    }

    public void setCodRespuesta(String codRespuesta) {
        this.codRespuesta = codRespuesta;
    }

    public String getMsgUsuario() {
        return msgUsuario;
    }

    public void setMsgUsuario(String msgUsuario) {
        this.msgUsuario = msgUsuario;
    }

    public String getMsgTecnico() {
        return msgTecnico;
    }

    public void setMsgTecnico(String msgTecnico) {
        this.msgTecnico = msgTecnico;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
