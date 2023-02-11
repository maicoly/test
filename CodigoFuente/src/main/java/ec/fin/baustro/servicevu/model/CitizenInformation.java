package ec.fin.baustro.servicevu.model;

public class CitizenInformation extends PersonalInformation {
    private String nui;
    private String birthPlace;
    private String birthProvince;
    private String address;
    private String educationLevel;

    public CitizenInformation(){
        super("","","","","","","","","","");
    }

    public CitizenInformation(String names,
                              String lastNames,
                              String fullName,
                              String birthDate,
                              String nationality,
                              String profession,
                              String fatherName,
                              String gender,
                              String motherName,
                              String civilStatus,
                              String nui,
                              String birthPlace,
                              String birthProvince,
                              String address,
                              String educationLevel) {
        super(names, lastNames, fullName, birthDate, nationality, profession, fatherName, gender, motherName, civilStatus);
        this.nui = nui;
        this.birthPlace = birthPlace;
        this.birthProvince = birthProvince;
        this.address = address;
        this.educationLevel = educationLevel;
    }

    public String getNui() {
        return nui;
    }

    public void setNui(String nui) {
        this.nui = nui;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBirthProvince() {
        return birthProvince;
    }

    public void setBirthProvince(String birthProvince) {
        this.birthProvince = birthProvince;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

}
