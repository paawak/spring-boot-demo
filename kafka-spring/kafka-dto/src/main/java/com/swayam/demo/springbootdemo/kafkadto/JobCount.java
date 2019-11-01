package com.swayam.demo.springbootdemo.kafkadto;

public class JobCount {

    public static final String ADMIN = "admin.";
    public static final String BLUE_COLLAR = "blue-collar";
    public static final String ENTREPRENEUR = "entrepreneur";
    public static final String HOUSEMAID = "housemaid";
    public static final String MANAGEMENT = "management";
    public static final String RETIRED = "retired";
    public static final String SELF_EMPLOYED = "self-employed";
    public static final String SERVICES = "services";
    public static final String STUDENT = "student";
    public static final String TECHNICIAN = "technician";
    public static final String UNEMPLOYED = "unemployed";
    public static final String UNKNOWN = "unknown";

    private int adminCount;
    private int blueCollarCount;
    private int entrepreneurCount;
    private int houseMaidCount;
    private int managementCount;
    private int retiredCount;
    private int selfEmployedCount;
    private int servicesCount;
    private int studentCount;
    private int technicianCount;
    private int unemployedCount;
    private int unknownCount;

    public int getAdminCount() {
	return adminCount;
    }

    public void setAdminCount(int adminCount) {
	this.adminCount = adminCount;
    }

    public int getBlueCollarCount() {
	return blueCollarCount;
    }

    public void setBlueCollarCount(int blueCollarCount) {
	this.blueCollarCount = blueCollarCount;
    }

    public int getEntrepreneurCount() {
	return entrepreneurCount;
    }

    public void setEntrepreneurCount(int entrepreneurCount) {
	this.entrepreneurCount = entrepreneurCount;
    }

    public int getHouseMaidCount() {
	return houseMaidCount;
    }

    public void setHouseMaidCount(int houseMaidCount) {
	this.houseMaidCount = houseMaidCount;
    }

    public int getManagementCount() {
	return managementCount;
    }

    public void setManagementCount(int managementCount) {
	this.managementCount = managementCount;
    }

    public int getRetiredCount() {
	return retiredCount;
    }

    public void setRetiredCount(int retiredCount) {
	this.retiredCount = retiredCount;
    }

    public int getSelfEmployedCount() {
	return selfEmployedCount;
    }

    public void setSelfEmployedCount(int selfEmployedCount) {
	this.selfEmployedCount = selfEmployedCount;
    }

    public int getServicesCount() {
	return servicesCount;
    }

    public void setServicesCount(int servicesCount) {
	this.servicesCount = servicesCount;
    }

    public int getStudentCount() {
	return studentCount;
    }

    public void setStudentCount(int studentCount) {
	this.studentCount = studentCount;
    }

    public int getTechnicianCount() {
	return technicianCount;
    }

    public void setTechnicianCount(int technicianCount) {
	this.technicianCount = technicianCount;
    }

    public int getUnemployedCount() {
	return unemployedCount;
    }

    public void setUnemployedCount(int unemployedCount) {
	this.unemployedCount = unemployedCount;
    }

    public int getUnknownCount() {
	return unknownCount;
    }

    public void setUnknownCount(int unknownCount) {
	this.unknownCount = unknownCount;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + adminCount;
	result = prime * result + blueCollarCount;
	result = prime * result + entrepreneurCount;
	result = prime * result + houseMaidCount;
	result = prime * result + managementCount;
	result = prime * result + retiredCount;
	result = prime * result + selfEmployedCount;
	result = prime * result + servicesCount;
	result = prime * result + studentCount;
	result = prime * result + technicianCount;
	result = prime * result + unemployedCount;
	result = prime * result + unknownCount;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	JobCount other = (JobCount) obj;
	if (adminCount != other.adminCount)
	    return false;
	if (blueCollarCount != other.blueCollarCount)
	    return false;
	if (entrepreneurCount != other.entrepreneurCount)
	    return false;
	if (houseMaidCount != other.houseMaidCount)
	    return false;
	if (managementCount != other.managementCount)
	    return false;
	if (retiredCount != other.retiredCount)
	    return false;
	if (selfEmployedCount != other.selfEmployedCount)
	    return false;
	if (servicesCount != other.servicesCount)
	    return false;
	if (studentCount != other.studentCount)
	    return false;
	if (technicianCount != other.technicianCount)
	    return false;
	if (unemployedCount != other.unemployedCount)
	    return false;
	if (unknownCount != other.unknownCount)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "JobCount [adminCount=" + adminCount + ", blueCollarCount=" + blueCollarCount
		+ ", entrepreneurCount=" + entrepreneurCount + ", houseMaidCount=" + houseMaidCount
		+ ", managementCount=" + managementCount + ", retiredCount=" + retiredCount
		+ ", selfEmployedCount=" + selfEmployedCount + ", servicesCount=" + servicesCount
		+ ", studentCount=" + studentCount + ", technicianCount=" + technicianCount
		+ ", unemployedCount=" + unemployedCount + ", unknownCount=" + unknownCount + "]";
    }

}
