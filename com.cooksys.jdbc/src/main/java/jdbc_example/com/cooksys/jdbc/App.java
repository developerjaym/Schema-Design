package jdbc_example.com.cooksys.jdbc;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
	
	static final String USER = "postgres";
	static final String PASS = "bondstone";
	
	
    public static void main( String[] args )
    {
	
    	
    	Person lucy = new PersonDao().get(1L);
    	lucy.setAge(50);
    	try {
			new PersonDao().save(lucy);
			System.out.println("Lucy is now " + new PersonDao().get(1L));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	/*
    	PersonDao pd = new PersonDao();
    	Person lucy = pd.get(1L);
    	Person tr = new Person();
    	tr.setAge(37);
    	tr.setFirstName("Am");
    	tr.setLastName("Frs");
    	tr.setLocation_id(2L);
    	tr.setInterests(lucy.getInterests());
    	
    	try {
			pd.save(tr);
			System.out.println("done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	*/
    	
    	/*Person mark = new Person();
    	mark.setAge(20);
    	mark.setFirstName("Mark");
    	mark.setLastName("Dwight");
    	mark.setLocation_id(2L);*/
    	
    	
    	/*
    	LocationDao l = new LocationDao();
    	//Location location = l.get(1L);
    	
    	Location baotou = new Location();
    	baotou.setCity("Erdos");
    	baotou.setCountry("China");
    	try {
    		l.save(baotou);
			//l.get(10L);
			System.out.println("this far!");
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	*/
    	//InterestDao i = new InterestDao();
    	//Interest interest = i.get(5L);
    	
    /*
    	Location location = new LocationDao().get(1L);
    	Interest interest = new InterestDao().get(1L);
    	
    	
    	JDBCTalker tdbct = new JDBCTalker();
    	Set<Long> setOfIds = tdbct.getResultsFromParameterizedQuery(interest, location);
    	Set<Person> setOfPeople = new HashSet<Person>();
    	setOfIds.forEach((Long personId)->{
    		setOfPeople.add(new PersonDao().get(personId));
    	});
    	System.out.println("Set of people: " + setOfPeople);
    	*/
    	
    	
    	
//    	System.out.println("interest: " + interest);
//    	System.out.println("location: " + location);
//    	System.out.println("A Set of people: " + pd.findInterestGroup(interest, location));
    	
    	
    	/*Connection conn = null;
    	try
    	{
    		//Step 2: register JDBC Driver
    		Class.forName(JDBC_DRIVER);
    		
    		//STEP 3: Opena  connection
    		System.out.println("connecting to a selected database");
    		conn = DriverManager.getConnection(DB_URL, USER, PASS);
    		Statement stmt = conn.createStatement();
    		//ResultSet rs = stmt.executeQuery("SELECT * FROM \"friendlr\".\"Person\"");
    		Scanner scanner = new Scanner(System.in);
    		System.out.println("Enter a city: ");
    		String location_param = scanner.nextLine();
    		
    		System.out.println("Enter an interest: ");
    		String interest_param = scanner.nextLine();
    		
    		ResultSet rs = stmt.executeQuery("Select \"firstName\",\"lastName\",\"age\",\"Title\", \"City\", \"State\", \"Country\""+ 
				"from \"friendlr\".\"person_interest\""+
				"join \"friendlr\".\"Person\""+
				"on \"friendlr\".\"person_interest\".\"person_id\"=\"friendlr\".\"Person\".\"person_id\""+
				"join \"friendlr\".\"Interest\""+
				"on \"Interest\".\"interest_id\" = \"person_interest\".\"interest_id\""+
				"join \"friendlr\".\"Location\""+
				"on \"friendlr\".\"Location\".\"location_id\" = \"friendlr\".\"Person\".\"location_id\""+
				"where \"City\" = '"+location_param+"' and \"Title\" = '"+interest_param+"'"+
				"order by \"Country\"");
    		
    		
//    		 * create a stored procedure based on that SQL query that takes an
//    		 * Interest and a Location as parameters and returns
//    		 * a Result Set of all Person entities that share 
//    		 * that Interest and Location
//    		 * 
//    		 SELECT * FROM Person WHERE Location="param1" AND Interest="param2"
    		 
    		
    		while(rs.next())
    		{
    			String firstName = rs.getString("firstName");
    			System.out.println(firstName);
    		}
    		stmt.close();
    		scanner.close();
    		System.out.println("Connected to database successfully");
    	}
    	catch(SQLException se)
    	{
    		se.printStackTrace();
    	} 
    	catch (ClassNotFoundException e) 
    	{
			e.printStackTrace();
		}
    	finally
    	{
    		try {
    			if(conn!=null)
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}*/
    }
}
