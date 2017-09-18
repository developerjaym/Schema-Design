package jdbc_example.com.cooksys.jdbc;

import java.util.HashMap;

public class LocationDao {
	
	JDBCTalker tdbct = new JDBCTalker();
	public Location get(Long id)
	{
		//return a location that accurately represents the row in the location table that has a Primary Key equal to the value provided as id
		HashMap<String, String> dr = new HashMap<String, String>();
		dr.put("location_id", id.toString());
		dr.put("City", null);
		dr.put("State", null);
		dr.put("Country", null);
		
		tdbct.getResultSet("select * from \"friendlr\".\"Location\" where \"location_id\"="+id.toString(), dr);
		Location p = new Location();
		p = new Location(Long.parseLong( dr.get("location_id")), dr.get("City"),
				dr.get("State"), dr.get("Country"));
		//rs.close();
		System.out.println("Your location is:");
		System.out.println("  " + p);
		return p; 
	}

	public void save(Location location) throws Exception
	{
		if(location.getId() != null && !tdbct.has("Location", "location_id", location.getId().toString()))
			throw new Exception("uh oh");
		
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("City", location.getCity());
		data.put("State", location.getState());
		data.put("Country", location.getCountry());
		
		tdbct.insert("Location", data, "location_id");
		System.out.println("I think I just inserted: " + data);
		
		if(location.getId() == null)
		{
			String newId = tdbct.insert("Location", data, "location_id");
			location.setId(Long.parseLong(newId));
		}
		else
		{
			tdbct.update("Interest", location.getId(), "location_id", data);
		}
		
		//save as new entry in the database if Id field is null
		//update if Id field is not null and already exists in database
		//an exception is thrown if the id field is not null and does not exist in the database
	}
	
}
