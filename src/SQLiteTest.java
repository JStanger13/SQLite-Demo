import java.sql.*;

public class SQLiteTest {
	private static Connection con;
	private static boolean hasData = false;
	
	public ResultSet displayUsers() throws ClassNotFoundException, SQLException{
		if(con == null){
			getConnection();
		}
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT fname, lname FROM user");
		return res;
	}

	private void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:/home/leo/work/mydatabase.db");
		initialise();
	}

	private void initialise() throws SQLException {
		// TODO Auto-generated method stub
		if(!hasData){
			hasData = true;
			Statement state = con.createStatement();
			ResultSet res = state.executeQuery("SELECT name FROM sqlite master WHERE type='table' AND name='user'");
			if( !res.next() ){
				System.out.println("Building the User table with prepopulated values.");
				// need to build a table
				Statement state2 = con.createStatement();
				state2.execute("CREATE TABLE user(id integer," + "fName varchar(60)," + "primary key(id);");
				
				// inserting some sample data
				PreparedStatement prep = con.prepareStatement("INSERT INTO user values(?,?,?);");
				prep.setString(2, "John");
				prep.setString(3, "McNeil");
				prep.execute();
			
				PreparedStatement prep2 = con.prepareStatement("INSERT INTO user values(?,?,?);");
				prep2.setString(2, "Paul");
				prep2.setString(3, "Smith");
				prep2.execute();	
			}
		}
	}
	public void addUser(String firstName, String lastName) throws ClassNotFoundException, SQLException{
		if(con == null){
			getConnection();
		}
		
		PreparedStatement prep = con.prepareStatement("INSRT INTO user values (?,?,?);");
		prep.setString(2, firstName);
		prep.setString(2, lastName);
		prep.execute();
	}
}
