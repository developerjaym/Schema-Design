package jdbc_example.com.cooksys.jdbc;

import java.util.HashMap;

public class InterestDao {
	
	JDBCTalker tdbct = new JDBCTalker();
	public Interest get(Long id)
	{
		//return an Interest that accurately represents the row in the Interest table that has a Primary Key equal to the value provided as id
		HashMap<String, String> dr = new HashMap<String, String>();
		dr.put("interest_id", id.toString());
		dr.put("Title", null);
		
		tdbct.getResultSet("select * from \"friendlr\".\"Interest\" where \"interest_id\"="+id.toString(), dr);
		Interest p = new Interest();
		p = new Interest(Long.parseLong( dr.get("interest_id")), dr.get("Title"));
		//rs.close();
		System.out.println("Your Interest is:");
		System.out.println("  " + p);
		return p;
	}
	
	public void save(Interest interest) throws Exception
	{
		if(interest.getId() != null && !tdbct.has("Interest", "interest_id", interest.getId().toString()))
			throw new Exception("uh oh");
		
		
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("Title", interest.getTitle());
		
		//tdbct.insert("Interest", data, "interest_id");
		//System.out.println("I think I just inserted: " + data);
		
		
		if(interest.getId() == null)
		{
			String newId = tdbct.insert("Interest", data, "interest_id");
			interest.setId(Long.parseLong(newId));
		}
		else
		{
			tdbct.update("Interest", interest.getId(), "interest_id", data);
		}
		
		
		//save as new entry in the database if Id field is null
		//update if Id field is not null and already exists in database
		//an exception is thrown if the id field is not null and does not exist in the database
	}
	
}
