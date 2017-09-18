package jdbc_example.com.cooksys.jdbc;

import java.util.Set;

public class Person {
	private Long id;
	private String firstName;
	private String lastName;
	private int age;
	
	private Location location;
	private Set<Interest> interests;
	
	
	private Long location_id;
	
	public Person()
	{
		
	}
	public Person(Long id, String firstName, String lastName, int age, Long location_id) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.location_id = location_id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Set<Interest> getInterests() {
		return interests;
	}
	public void setInterests(Set<Interest> interests) {
		this.interests = interests;
	}
	public Long getLocation_id() {
		return location_id;
	}
	public void setLocation_id(Long location_id) {
		this.location_id = location_id;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", location=" + location + ", interests=" + interests + ", location_id=" + location_id + "]";
	}
	
}
