import java.sql.*;

/**
  * This class defines a database connection on a local host to MariaDB.
  *
  * @author Mikayla Crawford
  */
public class DBConnection {

  private static final String JDBC_DRIVER = "org.mysql.jdbc.Driver";
  private static final String LOCAL_HOST = "jdbc:mariadb://localhost:3306/";

  private String db;
  private String username;
  private String password;
  private Connection connection;

  /**
    * Constructor for a DBConnection. The database MUST already exist. Allows
    * for portability on different machines.
    *
    * @param db the database to connect to
    * @param username the username in MariaDB
    * @param password the password in MariaDB
    */
  public DBConnection(String db, String username, String password) {
    this.db = db;
	  this.username = username;
	  this.password = password;
  }

  /**
    * This function attempts to open a connection with the driver.
    *
    * @return whether the connection was opened successfully or not
    */
  public boolean openConnection( ) {
	  boolean opened = false; //Tracks if the connection was made

  	 try {
      //Opens the connection
      connection = DriverManager.getConnection(LOCAL_HOST + db, username, password);
      System.out.println(db + " connected.\n"); //Alerts of successful connect

      opened = true;
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }

    return opened;
  }

  /**
    * This function attempts to prepare and execute a dynamic query based on
    * the arguments given.
    *
    * @param sql the complete sql statement with parameters
    * @param args[] the parameters to insert. The ith element corresponds to
    * the (i + 1)th "?". (SQL starts indeces at 1). A default of null is
    * returned.
    */
  public ResultSet query(String sql, String args[]) throws MallSqlException{
    ResultSet rs = null; //The result of the query
    int i; //Current index args

    try {
      //Prepare the statement
      PreparedStatement ps = connection.prepareStatement(sql);

      //If parameters exist
      if (args != null) {
        //Insert the parameters
        for (i = 0; i < args.length; i++) {
          ps.setString((i + 1), args[i]);
  	     }
  	   }

      rs = ps.executeQuery(); //Execute the query
    } catch (SQLException sqle) {
      throw new MallSqlException(sqle.getMessage()); //Throw my exception
    }

    return rs;
  }

  /**
    * This function updates (executes) the given complete sql statement. The
    * query is expected to return a single row integer result.
    *
    * @param sql the complete update. NO PARAMETERS
    * @return the integer result from the query. A default of 0 is returned.
    */
  public int update(String sql) throws MallSqlException {
    int result = 0;

    //Attempt to execute the statement
    try {
      PreparedStatement ps = connection.prepareStatement(sql);
      result = ps.executeUpdate();
    } catch (SQLException sqle) {
      throw new MallSqlException(sqle.getMessage()); //Throw my exception
    }

    return result;
  }

  /**
    * This function closes the connection with the driver if one is opened. As
    * good practice, this should always be done before ending the program.
    */
  public void closeConnection() {
    //Attempt to close the connection if one is open
    try {
      if (connection != null) {
      connection.close();
      }
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }
}
