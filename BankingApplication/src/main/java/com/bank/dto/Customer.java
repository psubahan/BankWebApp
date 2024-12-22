package com.bank.dto;

public class Customer 
{
	private long accNo;
	private String name;
	private long phone;
	private String email;
	private double bal;
	private int pin;
	public Customer(long accNo, String name, long phone, String email, double bal, int pin) {
		super();
		this.accNo = accNo;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.bal = bal;
		this.pin = pin;
	}
	public Customer() {
		super();
	}
	public long getAccNo() {
		return accNo;
	}
	public void setAccNo(long accNo) {
		this.accNo = accNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getBal() {
		return bal;
	}
	public void setBal(double bal) {
		this.bal = bal;
	}
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	@Override
	public String toString() {
		return "Customer [accNo=" + accNo + ", name=" + name + ", phone=" + phone + ", email=" + email + ", bal=" + bal
				+ "]";
	}
	
	
}
