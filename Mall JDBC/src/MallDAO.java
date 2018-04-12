import java.util.List;
import java.util.LinkedList;
import java.sql.*;
import javax.swing.JFrame;
import java.util.Vector;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;

/**
  * This class acts as a data access object (DAO) to communicate between the
  * JDBC GUI and Mall database. It supports queries and update.
  *
  * @author Mikayla Crawford
  */
  public class MallDAO {
    private static DBConnection dbc;

    /**
      * Default constructor. Initializes and opens a connection with the Mall
      * database.
      */
    MallDAO() {
      this.dbc = new DBConnection("Mall", "root", "sparrow7");
      dbc.openConnection();
    }

    /**
      * This method handles all modification statements for the database.
      *
      * @param sql the update statement to execute
      */
    public int updateQuery(String sql) {
      int rowsAffected = 0;

      try {
        rowsAffected = dbc.update(sql);
      } catch(MallSqlException sqle) {
        throw sqle;
      }

      return rowsAffected;
    }

    /**
      * This method gets all the tuples from the specified relation.
      *
      * @return the result set containing all tuples in the relation
      */
    public DefaultTableModel getTuples(String relation) {
      String sql = "SELECT * FROM " + relation;
      return getTableFromQuery(sql);
    }

    /**
      * This method creates a table based on the passed in query.
      *
      * @throws MallSqlException if there is a problem with the sql syntax
      * @param sql the sql string to execute
      * @return the table of the result
      */
    public DefaultTableModel getTableFromQuery(String sql) {
      Vector<Vector<Object>> data = new Vector<Vector<Object>>(); //Table data
      Vector<String> columnNames = new Vector<String>(); //Name of attributes

      try {
        ResultSet result = dbc.query(sql, null);
        ResultSetMetaData metaData = result.getMetaData();

        //Get attribute names
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
          columnNames.add(metaData.getColumnName(column));
        }

        //Get tuples
        while (result.next()) {
          Vector<Object> vector = new Vector<Object>();
          for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
              vector.add(result.getObject(columnIndex));
          }
          data.add(vector);
        }

      } catch (SQLException sqle) {
        throw new MallSqlException(sqle.getMessage());
      } catch (NullPointerException e) {
      }

      return new DefaultTableModel(data, columnNames);
    }

    /**
      * This method gets all of the column (attribute) names from the specified
      * relation and returns them in a list.
      *
      * @param relation the relation to get column names from
      * @return a list of the column names
      */
    public List<String> getAllColumns(String relation) {
      String sql = "SELECT * FROM " + relation;
      ResultSet result = dbc.query(sql, null); //Gets all the table names
      List<String> colNames = new LinkedList<String>();

      try {
        ResultSetMetaData metaData = result.getMetaData(); //Get meta data

        //Add each column name to the list
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
          colNames.add(metaData.getColumnName(i));
        }
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }

      return colNames;
    }

    /**
      * This function obtains all the table names in the database in a list.
      *
      * @return a list of the table names
      */
    public List<String> getTableNames() {
      String sql = "SHOW TABLES;";
      ResultSet result = dbc.query(sql, null); //Gets all the table names
      List<String> list = new LinkedList<String>(); //The list to add to

      try {
        //Extracts all the table names
        while (result.next()) {
          list.add(result.getString("TABLE_NAME"));
        }
      } catch (SQLException e) {}

      return list;
    }
  }
