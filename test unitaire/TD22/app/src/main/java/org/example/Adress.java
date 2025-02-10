package org.example;

public class Adress {
	private String City;
	private String Country;
	
	public Adress(String city, String country) {
		super();
		City = city;
		Country = country;
	}
	public String getCountry() {
		return Country;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public void setCountry(String country) {
		Country = country;
	}
}
