import java.sql.SQLException;

/**
  * This class defines a custom throwable exception for the Mall database. It
  * is intended to be thrown when an advanced user can perform an SQL injection.
  *
  * @author Mikayla Crawford
  */
public class MallSqlException extends RuntimeException {

      /**
        * Default constructor.
        */
      public MallSqlException() {}

      /**
        * Constructor which takes a message.
        */
      public MallSqlException(String message) {
         super(message);
      }
 }
