import javax.swing.table.*;
import javax.swing.*;
import java.awt.event.*;

/**
  * Class used to define events for radio buttons used to handle column queries
  * for the Mall SQL Assistant.
  *
  * @author Mikayla Crawford
  */
public class ColBtnAction extends AbstractAction {
  private JTable table;
  private TableColumn col;
  private boolean checked;

  /**
    * Constructor for an action which takes the table column.
    *
    * @param table the table to add/remove from
    * @param col the column which the button controls
    * @param checked the initial checked state of the button
    */
  ColBtnAction(JTable table, TableColumn col, boolean checked) {
    this.table = table;
    this.col = col;
    this.checked = checked;
  }

  /**
    * @Override
    */
  public void actionPerformed(ActionEvent e) {
    if (checked) { //Box is becoming unchecked
      table.getColumnModel().removeColumn(col);
    } else { //Box is becoming checked
      table.getColumnModel().addColumn(col);

    }

    checked = !checked; //Updates checked
  }
}
