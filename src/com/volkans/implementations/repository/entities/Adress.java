package com.volkans.implementations.repository.entities;

public final class Adress { // final class : burdan kimse kalıtım alamasın
	private String city;
	private String country;
	private String street;
	private String postalCode;
	
	public Adress() {
		super();
	}
	
	public Adress(String city, String country, String street, String postalCode) {
		this();
		setCity(city);
		setCountry(country);
		setStreet(street);
		setPostalCode(postalCode);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return "\nÜlke: " + getCountry() + "\nŞehir: " + getCity() + "\nCadde: " + getStreet() + "\nPosta Kodu: " + getPostalCode();
	}
	
}
