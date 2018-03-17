package main.references;

public class Model {

	private Integer number;
	
	private String name;
	
	private Child child;

	public Model(String name) {
		
	number = 22;
	name = name;
	child = new Child();
	
	}
	
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Child getChild() {
		return child;
	}

	public void setChild(Child child) {
		this.child = child;
	}
	
	
	
	
}
