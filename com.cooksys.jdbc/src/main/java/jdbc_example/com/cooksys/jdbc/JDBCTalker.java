package jdbc_example.com.cooksys.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JDBCTalker {
	
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
	
	static final String USER = "postgres";
	static final String PASS = "bondstone";
	
	
	public void updatePerson(String tableName, Person person)
	{
		Connection conn = null;
    	try
    	{
    		//Step 2: register JDBC Driver
    		Class.forName(JDBC_DRIVER);
    		
    		//STEP 3: Opena  connection
    		System.out.println("connecting to a selected database");
    		conn = DriverManager.getConnection(DB_URL, USER, PASS);
    		Statement stmt = conn.createStatement();
    		
    		String deleteQuery = "delete from \"friendlr\".\""+tableName+"\" where \"person_id\"='"+person.getId()+"'";
    		
    		
    		//delete all that person's interests first
    		System.out.println("Query " + deleteQuery);
    		stmt.execute(deleteQuery);
    		
    		System.out.println("person's interests: " + person.getInterests());
    		//then insert into person_interest all that person's interests
    		person.getInterests().forEach((Interest interest)->{
    			HashMap<String, String> interestMap = new HashMap<>();
    			interestMap.put("person_id", person.getId().toString());
    			interestMap.put("interest_id", interest.getId().toString());
    			System.out.println("Inserting..." + interestMap +  " into " + tableName);
    			insert(tableName, interestMap, "person_id");
    		});
    		stmt.close();
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
				e.printStackTrace();
			}
    	}
	}
	
	public String insert(String tableName, Map<String, String> data, String attribute)
	{
		Connection conn = null;
    	try
    	{
    		//Step 2: register JDBC Driver
    		Class.forName(JDBC_DRIVER);
    		
    		//STEP 3: Opena  connection
    		System.out.println("connecting to a selected database");
    		conn = DriverManager.getConnection(DB_URL, USER, PASS);
    		Statement stmt = conn.createStatement();
    		
    		final StringBuilder queryPart1 = new StringBuilder("insert into \"friendlr\".\""+tableName+"\"(");
    		final StringBuilder queryPart2 = new StringBuilder(" values (");
    		data.forEach((String columnName, String value)->{
    			if(value != null)
    			{
    				queryPart1.append("\""+columnName+"\",");
    				queryPart2.append("'"+value+"',");
    			}
    			else
    				System.out.println("null value: " + columnName);
    		});
    		String qp1 = queryPart1.toString().substring(0, queryPart1.toString().length()-1) + ")";//get rid of final comma
    		String qp2 = queryPart2.toString().substring(0, queryPart2.toString().length()-1) + ");";//get rid of final comma
    		
    		String query = qp1 + qp2;
    		
    		System.out.println("Query " + query);
    		
    		PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    		ps.executeUpdate();
    		ResultSet rs = ps.getGeneratedKeys();
    		System.out.println(rs.toString());
    		
    		String returnString = "";
    		rs.next();
    		returnString = rs.getString(attribute);//"person_id");
    	
    		
    		
    		stmt.close();
    		rs.close();
    		System.out.println("Connected to database successfully");
    		return returnString;
    	}
    	catch(SQLException se)
    	{
    		se.printStackTrace();
    		return null;
    	} 
    	catch (ClassNotFoundException e) 
    	{
			e.printStackTrace();
			return null;
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
    	}

	}
	public void getResultSet(String query, Map<String, String> desiredResults)
	{
		Connection conn = null;
    	try
    	{
    		//Step 2: register JDBC Driver
    		Class.forName(JDBC_DRIVER);
    		
    		//STEP 3: Opena  connection
    		//System.out.println("connecting to a selected database");
    		conn = DriverManager.getConnection(DB_URL, USER, PASS);
    		Statement stmt = conn.createStatement();
    		
    		//System.out.println("Query " + query);
    		ResultSet rs = stmt.executeQuery(query);
    		
    		//rs.next();
    		while(rs.next())
    		{
    			desiredResults.forEach((String columnName, String result)->{
        			try {
    					result = rs.getString(columnName);
    					desiredResults.put(columnName, result);
    			//		System.out.println("result: " + result);
    				} catch (SQLException e) {
    					e.printStackTrace();
    				}
        		});
        		//System.out.println(" map : " + desiredResults);
    		}
    		
    		
    		stmt.close();
    		rs.close();
    		//System.out.println("Connected to database successfully");
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
    	}
	}
	public Set<Long> getResultsFromParameterizedQuery(Interest interest, Location location)
	{
		HashSet<Long> setOfIds = new HashSet<>();
		Connection conn = null;
    	try
    	{
    		//Step 2: register JDBC Driver
    		Class.forName(JDBC_DRIVER);
    		
    		//STEP 3: Opena  connection
    		System.out.println("connecting to a selected database");
    		conn = DriverManager.getConnection(DB_URL, USER, PASS);
    		Statement stmt = conn.createStatement();
    		
    		String location_param = location.getCity();
    		String interest_param = interest.getTitle();
    		
    		ResultSet rs = stmt.executeQuery("Select \"friendlr\".\"Person\".\"person_id\", \"firstName\",\"lastName\",\"age\",\"Title\", \"City\", \"State\", \"Country\""+ 
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
    		 
    		System.out.println("Below is a list of all people with that interest and location: " );
    		
    		while(rs.next())
    		{
    			setOfIds.add(Long.parseLong(rs.getString("person_id")));
    			System.out.println(" " + rs.getString("firstName") + " " + rs.getString("lastName"));
    		}
    		stmt.close();
    		System.out.println("Connected to database successfully");
    		return setOfIds;
    	}
    	catch(SQLException se)
    	{
    		se.printStackTrace();
    		return setOfIds;
    	} 
    	catch (ClassNotFoundException e) 
    	{
			e.printStackTrace();
			return setOfIds;
		}
    	finally
    	{
    		try {
    			if(conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}

	}
	public Set<Interest> getInterests(Long id) {
		
		Set<Interest> setOfInterests = new HashSet<Interest>();
		Connection conn = null;
    	try
    	{
    		//Step 2: register JDBC Driver
    		Class.forName(JDBC_DRIVER);
    		
    		//STEP 3: Opena  connection
    		System.out.println("connecting to a selected database");
    		conn = DriverManager.getConnection(DB_URL, USER, PASS);
    		Statement stmt = conn.createStatement();
    		
    		String query = "select * from \"friendlr\".\"person_interest\" where \"person_id\"='"+id+"'";
    		System.out.println("Query " + query);
    		ResultSet rs = stmt.executeQuery(query);
    		
    		System.out.println(rs.toString());
    		
    		InterestDao idao = new InterestDao();
    		
    		while(rs.next())
    		{
    			setOfInterests.add(idao.get(Long.parseLong(rs.getString("interest_id"))));
    		}
    		
    		
    		stmt.close();
    		rs.close();
    		System.out.println("set of interests: " + setOfInterests);
    		System.out.println("Connected to database successfully");
    		return setOfInterests;
    	}
    	catch(SQLException se)
    	{
    		se.printStackTrace();
    		return setOfInterests;
    	} 
    	catch (ClassNotFoundException e) 
    	{
			e.printStackTrace();
			return setOfInterests;
		}
    	finally
    	{
    		try {
    			if(conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
		
	}

	public void update(String tableName/*, Person person*/, Long id, String idName, HashMap<String, String> data) {
		Connection conn = null;
    	try
    	{
    		//Step 2: register JDBC Driver
    		Class.forName(JDBC_DRIVER);
    		
    		//STEP 3: Opena  connection
    		System.out.println("connecting to a selected database");
    		conn = DriverManager.getConnection(DB_URL, USER, PASS);
    		Statement stmt = conn.createStatement();
    		
    		final StringBuilder queryPart1 = new StringBuilder("update \"friendlr\".\""+tableName+"\" set ");
    		final StringBuilder queryPart2 = new StringBuilder(" where ");
    		data.forEach((String columnName, String value)->{
    			if(value != null)
    			{
    				queryPart1.append("\""+columnName+"\"='"+value+"',");//set
    			}
    			else
    				System.out.println("null value: " + columnName);
    		});
    		queryPart2.append("\""+idName+"\"='"+/*person.getId()*/id+"\'");
    		String qp1 = queryPart1.toString().substring(0, queryPart1.toString().length()-1);//get rid of final comma
    		String qp2 = queryPart2.toString();//.substring(0, queryPart2.toString().length()-1) + ");";//get rid of final comma
    		
    		String query = qp1 + qp2;
    		
    		System.out.println("Query " + query);
    		
    		PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    		ps.executeUpdate();
    		ResultSet rs = ps.getGeneratedKeys();
    		System.out.println(rs.toString());
    		
    		stmt.close();
    		rs.close();
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
				e.printStackTrace();
			}
    	}

		
	}
	public boolean has(String tableName, String columnName, String value)
	{
		//check this table for this value in this column
			//return false if there are no rows returned
		Connection conn = null;
    	try
    	{
    		//Step 2: register JDBC Driver
    		Class.forName(JDBC_DRIVER);
    		
    		//STEP 3: Opena  connection
    		conn = DriverManager.getConnection(DB_URL, USER, PASS);
    		Statement stmt = conn.createStatement();
    		
    		String query = "select * from \"friendlr\".\""+tableName+"\" where \""+columnName+"\"='"+value+"'";
    		System.out.println(" has Query " + query);
    		ResultSet rs = stmt.executeQuery(query);
    		
    		//rs.next();
    		StringBuilder sb = new StringBuilder();
    		while(rs.next())
    		{
    			sb.append(rs.getString(columnName));
    		}
    		boolean b = !sb.toString().isEmpty();
    		
    		stmt.close();
    		rs.close();
    		return b;
    	}
    	catch(SQLException se)
    	{
    		se.printStackTrace();
    		return false;
    	} 
    	catch (ClassNotFoundException e) 
    	{
			e.printStackTrace();
			return false;
		}
    	finally
    	{
    		try {
    			if(conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
		
	}
}
