package ec.fin.baustro.servicevu.model;

import java.util.HashMap;
import java.util.Map;

public class RegistroCivil {

    private String usr;
    private String can;
    private Map<String, Object> ctl = new HashMap<>();
    private String codRespuesta;
    private String msgUsuario;
    private String msgTecnico;

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getCan() {
        return can;
    }

    public void setCan(String can) {
        this.can = can;
    }

    public Map<String, Object> getCtl() {
        return ctl;
    }

    public void setCtl(Map<String, Object> ctl) {
        this.ctl = ctl;
    }

    public String getCodRespuesta() {
        return codRespuesta;
    }

    public void setCodRespuesta(String responseCod) {
        this.codRespuesta = responseCod;
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

    @Override
    public String toString() {
        return "{" +
                "responseCode:'" + codRespuesta + '\'' +
                ", messageUser:'" + msgUsuario + '\'' +
                ", messageTec:'" + msgTecnico + '\'' +
                '}';
    }
}
