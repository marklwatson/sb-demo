package simple;

public class SimpleObject {

	private String name;
	private String description;
	private int age;
	
	public SimpleObject(){}
	
	public SimpleObject(String name, int age){
		this.setName(name);
		this.setDescription(name + "'s Simple Object");
		this.setAge(age);
	}
	
	public int getAge() {
		return age;
	}
	public String getDescription() {
		return description;
	}
	public String getName() {
		return name;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setName(String name) {
		this.name = name;
	}
}
