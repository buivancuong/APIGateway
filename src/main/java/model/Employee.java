package model;

public class Employee {

    private String idEmployee;
    private String nameEmployee;
    private String positionEmployee;
    private String ageEmployee;
    private String genderEmployee;

    public Employee() {}

    private Employee (BuilderEmployee builderEmployee) {
        this.idEmployee = builderEmployee.idEmployee;
        this.nameEmployee = builderEmployee.nameEmployee;
        this.positionEmployee = builderEmployee.positionEmployee;
        this.ageEmployee = builderEmployee.ageEmployee;
        this.genderEmployee = builderEmployee.genderEmployee;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }

    public String getPositionEmployee() {
        return positionEmployee;
    }

    public void setPositionEmployee(String positionEmployee) {
        this.positionEmployee = positionEmployee;
    }

    public String getAgeEmployee() {
        return ageEmployee;
    }

    public void setAgeEmployee(String ageEmployee) {
        this.ageEmployee = ageEmployee;
    }

    public String getGenderEmployee() {
        return genderEmployee;
    }

    public void setGenderEmployee(String genderEmployee) {
        this.genderEmployee = genderEmployee;
    }

    public static class BuilderEmployee {
        private String idEmployee = "";
        private String nameEmployee = "";
        private String positionEmployee = "";
        private String ageEmployee = "";
        private String genderEmployee = "";

        public BuilderEmployee setIdEmployee(String idEmployee) {
            this.idEmployee = idEmployee;
            return this;
        }

        public BuilderEmployee setNameEmployee(String nameEmployee) {
            this.nameEmployee = nameEmployee;
            return this;
        }

        public BuilderEmployee setPositionEmployee(String positionEmployee) {
            this.positionEmployee = positionEmployee;
            return this;
        }

        public BuilderEmployee setAgeEmployee(String ageEmployee) {
            this.ageEmployee = ageEmployee;
            return this;
        }

        public BuilderEmployee setGenderEmployee(String genderEmployee) {
            this.genderEmployee = genderEmployee;
            return this;
        }

        public Employee builder() {
            return new Employee(this);
        }
    }

}
