package ec.fin.baustro.servicevu.model;

public class InputConfigurationsVu {
    private String canal;
    private String proceso;

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    @Override
    public String toString() {
        return "{" +
                "canal:'" + canal + '\'' +
                ", proceso:'" + proceso + '\'' +
                '}';
    }
}
