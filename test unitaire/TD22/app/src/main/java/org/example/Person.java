package org.example;

public class Person {
	private String Name;
	private Integer Age;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Integer getAge() {
		return Age;
	}
	public void setAge(Integer age) {
		Age = age;
	}
	public Adress getAdress() {
		return Adress;
	}
	public void setAdress(Adress adress) {
		Adress = adress;
	}
	public Person(String name, Integer age) {
		super();
		Name = name;
		Age = age;
	}
	public Person(String name, Integer age, org.example.Adress adress) {
		super();
		Name = name;
		Age = age;
		Adress = adress;
	}
	private Adress Adress;
}
