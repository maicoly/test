package ec.fin.baustro.servicevu.model;

public class PersonalInformation {
    private String names;
    private String lastNames;
    private String fullName;
    private String birthDate;
    private String nationality;
    private String profession;
    private String fatherName;
    private String gender;
    private String motherName;
    private String civilStatus;

    public PersonalInformation(String names, String lastNames, String fullName, String birthDate, String nationality, String profession, String fatherName, String gender, String motherName, String civilStatus) {
        this.names = names;
        this.lastNames = lastNames;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.profession = profession;
        this.fatherName = fatherName;
        this.gender = gender;
        this.motherName = motherName;
        this.civilStatus = civilStatus;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }
}
