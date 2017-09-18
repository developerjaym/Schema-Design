package jdbc_example.com.cooksys.jdbc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PersonDao {
	JDBCTalker tdbct = new JDBCTalker();
	public Person get(Long id)
	{
		//return a person that accurately represents the row in the Person table that has a Primary Key equal to the value provided as id
		HashMap<String, String> dr = new HashMap<String, String>();
		dr.put("person_id", id.toString());
		dr.put("firstName", null);
		dr.put("lastName", null);
		dr.put("age", null);
		dr.put("location_id", null);
		
		tdbct.getResultSet("select * from \"friendlr\".\"Person\" where \"person_id\"="+id.toString(), dr);
		Person p = new Person();
		p = new Person(id, dr.get("firstName"),
				dr.get("lastName"), Integer.parseInt(dr.get("age")), Long.parseLong(dr.get("location_id")));
		
		Set<Interest> setOfInterests = tdbct.getInterests(id);//each person should have accurate interests
		
		p.setInterests(setOfInterests);
		
		LocationDao ld = new LocationDao();
		p.setLocation(ld.get(p.getLocation_id()));
		
		return p;
		
	}
	public void save(Person person) throws Exception
	{
		if(person.getId() != null && !tdbct.has("Person", "person_id", person.getId().toString()))
			throw new Exception("uh oh");
		
		
		HashMap<String, String> data = new HashMap<String, String>();
		//data.put("person_id", null);
		data.put("firstName", person.getFirstName());
		data.put("lastName", person.getLastName());
		data.put("age", person.getAge()+"");
		data.put("location_id", person.getLocation_id()+"");
		if(person.getId() == null)
		{
			String newId = tdbct.insert("Person", data, "person_id");
			person.setId(Long.parseLong(newId));
		}
		else
		{
			tdbct.update("Person", person.getId(), "person_id", data);
		}
		//update any related join table
		tdbct.updatePerson("person_interest", person);
		
		
		//save as new entry in the database if Id field is null
		//update if Id field is not null and already exists in database
		//an exception is thrown if the id field is not null and does not exist in the database
	
		//also update the Location Table, the Interest Table, and any related join tables or join columns,
		//so that the contents of the saved Person object are stored accurately
	}
	public Set<Person> findInterestGroup(Interest interest, Location location)
	{
		Set<Long> firstSet = tdbct.getResultsFromParameterizedQuery(interest, location);
		Set<Person> personSet = new HashSet<Person>();
		firstSet.forEach((Long id)->{
			personSet.add(get(id));
		});
		return personSet;
		//executes the stored procedure from schema design assignment
		//transforms the resultset obtained from executing the stored procedure into a Set<Person.
		//returns the Set<Person.
	}
}
