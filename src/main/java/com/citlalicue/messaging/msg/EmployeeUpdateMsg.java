package com.citlalicue.messaging.msg;


public class EmployeeUpdateMsg {

    private long employeeId;
    private String firstName;
    private String lastName;
    private String workDepartment;
    private String job;
    private String gender;
    private String hireDate;
    private String birthDate;
    private Double salary;
    private String project;
    private String address;
    private String skillName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public EmployeeUpdateMsg() {}


    public EmployeeUpdateMsg(long employeeId, String firstName, String lastName, String workDepartment,
        String job, String gender, String hireDate, String birthDate, Double salary, String project,
        String address, String skillName) {
            super();
            this.employeeId = employeeId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.workDepartment = workDepartment;
            this.job = job;
            this.gender = gender;
            this.hireDate = hireDate;
            this.birthDate = birthDate;
            this.salary = salary;
            this.project = project;
            this.address = address;
            this.skillName = skillName;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getWorkDepartment() {
        return workDepartment;
    }

    public void setWorkDepartment(String workDepartment) {
        this.workDepartment = workDepartment;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    @Override
    public String toString() {
        return new StringBuilder("EmployeeUpdateMsg")
        .append("employeeId=").append(employeeId)
        .append(", firstName=").append(firstName)
        .append(", lastName=").append(lastName)
        .append(", workDepartment=").append(workDepartment)
        .append(", job=").append(job)
        .append(", gender=").append(gender)
        .append(", hireDate=").append(hireDate)
        .append(", birthDate=").append(birthDate)
        .append(", salary=").append(salary)
        .append(", project=").append(project)
        .append(", address=").append(address)
        .append(", skillName=").append(skillName).toString();
    }

}