package com.swayam.demo.springbootdemo.kafkastream.dto;

import java.math.BigDecimal;

public class BankDetail {

	private int id;
	private int age;
	private String job;
	private String marital;
	private String education;
	private String defaulted;
	private BigDecimal balance;
	private String housing;
	private String loan;
	private String contact;
	private int day;
	private String month;
	private int duration;
	private int campaign;
	private int pdays;
	private int previous;
	private String poutcome;
	private String y;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getMarital() {
		return marital;
	}

	public void setMarital(String marital) {
		this.marital = marital;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getDefaulted() {
		return defaulted;
	}

	public void setDefaulted(String defaulted) {
		this.defaulted = defaulted;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getHousing() {
		return housing;
	}

	public void setHousing(String housing) {
		this.housing = housing;
	}

	public String getLoan() {
		return loan;
	}

	public void setLoan(String loan) {
		this.loan = loan;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getCampaign() {
		return campaign;
	}

	public void setCampaign(int campaign) {
		this.campaign = campaign;
	}

	public int getPdays() {
		return pdays;
	}

	public void setPdays(int pdays) {
		this.pdays = pdays;
	}

	public int getPrevious() {
		return previous;
	}

	public void setPrevious(int previous) {
		this.previous = previous;
	}

	public String getPoutcome() {
		return poutcome;
	}

	public void setPoutcome(String poutcome) {
		this.poutcome = poutcome;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "BankDetail [id=" + id + ", age=" + age + ", job=" + job + ", marital=" + marital + ", education="
				+ education + ", defaulted=" + defaulted + ", balance=" + balance + ", housing=" + housing + ", loan="
				+ loan + ", contact=" + contact + ", day=" + day + ", month=" + month + ", duration=" + duration
				+ ", campaign=" + campaign + ", pdays=" + pdays + ", previous=" + previous + ", poutcome=" + poutcome
				+ ", y=" + y + "]";
	}

}
