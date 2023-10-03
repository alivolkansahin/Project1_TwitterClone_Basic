package com.volkans.implementations.repository.entities;

public non-sealed final class Admin extends Person{ // final class : burdan kimse kalıtım alamasın
	private String mail;

	public Admin(String name, String surname, String username, String password, String phoneNumber, String registerDate) {
		super(name, surname, username, password, phoneNumber, registerDate);
		setMail(username + "@twitter.com"); // admin oluşturulurken onun adına böyle bir mail adresi otomatik oluşturulsun
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return "Admin: " + getName() + " " + getSurname() + " - Username: " + getUsername() + " - Mail: " + getMail() 
				      + " - Şifre: " + getPassword() + " - Register Date: " + getRegisterDate() + " - ID: " + getId();
	}
	
}
