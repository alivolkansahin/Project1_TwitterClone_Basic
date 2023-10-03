package com.volkans.implementations.repository.entities;

import java.util.Random;
/**
 * Admin ve User'ın ortak özelliklerini içerecek olan SuperClass Sınıfı.
 * Sealed keywordü ile sadece Admin ve User sınıflarına miras alma müsade edilecek.
 * Abstract keywordü sayesinde bu sınıftan nesne üretilmesine müsade edilmeyecek.
 */
public sealed abstract class Person permits Admin, User{
	static Random rnd = new Random(); // Package friendly 1 tane Random Sınıfı Nesnesi - bütün entities ID işlemlerinde kullanılacak.
	private String name;
	private String surname;
	private String username;
	private String password;
	private String phoneNumber;
	private Adress adress;
	private String id;
	private String registerDate; // LocalDate (not LocalDateTime)
	
	public Person() {
		super();
	}

	public Person(String name, String surname, String username, String password, String phoneNumber, String registerDate) {
		this();
		setName(name);
		setSurname(surname);
		setUsername(username);
		setPassword(password);
		setPhoneNumber(phoneNumber);
		setAdress(new Adress()); // boş adresli üretilecek
		setId(Integer.toString(rnd.nextInt(1_000_000,10_000_000))); // 7 haneli random bir sayı olsun ID'si
		setRegisterDate(registerDate); // database için önceden elle giriliyor, yeni kayıtlara localedate ile girilecek.
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	
}
