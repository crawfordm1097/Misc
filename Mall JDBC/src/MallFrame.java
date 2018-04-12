import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.table.*;
import javax.swing.BorderFactory;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;


/* TODO:
2) Add functionality for normal user input restriction queries (make result appear in separate dialog)
  -input for every string attribute (and ids), numeric range for prices (maybe just inputs) -> maybe just sort by low to high
  -Refinements aren't made until search is clicked
  -Updates aren't made until update is clicked (editing existing tuple will be from directly editable cells; need to separate between add new tuple and deleting)
  -Cells can be directly edited but need to store the results in the database
  -Need to add triggers in maria to get propagation of updates, inserts, and deletes to work
*/

/**
  * This class defines the Mall SQL Assistant.
  *
  * @author Mikayla Crawford
  */
public class MallFrame extends JFrame {
  private final int WIDTH = 800;
  private final int HEIGHT = 620;
  private MallDAO dao;
  private JPanel mainPane;

  /**
    * Default constructor for the Mall GUI.
    */
  MallFrame() {
    dao = new MallDAO(); //Intializes the DOA
    mainPane = new JPanel();
    mainPane.setLayout(new BorderLayout(10, 10));
    setTitle("Mall SQL Assistant"); //Sets title of GUI
    setSize(WIDTH, HEIGHT); //Sets size
    setupAdvanced();
    intiliazeTabs();
    setupFooter();

    add(mainPane);
  }

  /**
    * This method sets up the advanced user button and interaction. It creates
    * the button and defines events and listeners. When the button is clicked,
    * a dialog opens asking for the SQL statement. If entered, it opens another
    * dialog with the table of result. If there is an error with the statement,
    * a dialog opens with a summary of the error statement. It is intended to
    * be used by those with SQL experience and is at risk for an SQL injection.
    */
  public void setupAdvanced() {
    JButton aBtn = new JButton("Advanced"); //Creates button

    //Defines event for button click
    ActionListener aBtnAction = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //Gets the sql statement from the user
        String sql = JOptionPane.showInputDialog(mainPane, "Please enter your SQL statement:");

        //If a statement was typed, execute it first as an update
        if (sql != null) {
          //Try to execute as query
          try {
            DefaultTableModel result = dao.getTableFromQuery(sql);
            JTable table = new JTable(result) {
              public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(500, 150);
              }
            };

            //Show result
            JOptionPane.showMessageDialog(mainPane, new JScrollPane(table), "Query Result", JOptionPane.INFORMATION_MESSAGE);
          } catch(MallSqlException mSql) {
            JOptionPane.showMessageDialog(mainPane, mSql.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }

          // try {
          //   int rowsAffected = dao.updateQuery(sql);
          //
          //   if (rowsAffected == 0) { //No matching rows
          //     JOptionPane.showMessageDialog(mainPane, "No matching data found for update.", "Failure", JOptionPane.ERROR_MESSAGE);
          //   } else { //Matching rows
          //     JOptionPane.showMessageDialog(mainPane, "Update made successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
          //   }
          // } catch (MallSqlException sqle) { //Any SQLException
          //   //Try to execute as query
          //   try {
          //     DefaultTableModel result = dao.getTableFromQuery(sql);
          //     JTable table = new JTable(result) {
          //       public Dimension getPreferredScrollableViewportSize() {
          //         return new Dimension(500, 150);
          //       }
          //     };
          //
          //     //Show result
          //     JOptionPane.showMessageDialog(mainPane, new JScrollPane(table), "Query Result", JOptionPane.INFORMATION_MESSAGE);
          //   } catch(MallSqlException mSql) {
          //     JOptionPane.showMessageDialog(mainPane, mSql.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          //   }
          // }
        }
      }
    };

    aBtn.addActionListener(aBtnAction);

    JPanel btnPanel = new JPanel(new BorderLayout());
    btnPanel.add(aBtn, BorderLayout.WEST);
    btnPanel.setPreferredSize(new Dimension(WIDTH, 40));
    btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));

    mainPane.add(btnPanel, BorderLayout.NORTH);
  }

  /**
    * This method initializes a tabbed pane for every relation in Mall. It
    * populates the pane with a table of tuples, radio buttons for querying
    * based on columns (SELECT ____), and buttons for specifying the choices as
    * search or query.
    */
  public void intiliazeTabs() {
    List<String> tabNames = dao.getTableNames(); //Gets all the relation names
    JPanel panel; //Container for holding everything for each tab
    JPanel btmPanel; //Container for holding everything below the table

    //Used for tabs
    JTabbedPane tabbedPane = new JTabbedPane(); //The tabbed pane to add to
    tabbedPane.setFont(new Font("Calibri", Font.BOLD, 19)); //Sets font for tabs
    tabbedPane.setSize(600, 200);

    //Used for table
    JTable table; //Blank table to add tuples to
    JScrollPane scrollPane; //Blank scroll pane to add table to

    //Creates tab for each relation
    for (String name : tabNames) {
      panel = new JPanel(); //Needed since a component can only be added once
      panel.setSize(750, 300);

      btmPanel = new JPanel(new BorderLayout(45, 45));

      //Creates table for each relation
      table = new JTable(dao.getTuples(name)) {
        public Dimension getPreferredScrollableViewportSize() {
          return new Dimension(750, 100);
        }
      };

      table.setFont(new Font("Calibri", Font.PLAIN, 15)); //Sets font for table

      //Adds table to scrollPane (needed for attr names to be seen) and adds border
      scrollPane = new JScrollPane(table);
      scrollPane.setBorder(BorderFactory.createLineBorder(Color.black)); //Adds border to scroll pane
      panel.add(scrollPane);

      //Creating radio buttons for each attribute
      List<String> colNames = dao.getAllColumns(name); //Gets all the column names for the specific relation

      JPanel rBtnPanel = new JPanel();

      //Creates radio buttons for each attribute
      int count = 0; //Tracks index of curr column
      for (String attr : colNames) {

        //Create and add button initliazed to selected
        JRadioButton btn = new JRadioButton(attr);
        btn.setSelected(true);
        btn.addActionListener(new ColBtnAction(table, table.getColumnModel().getColumn(count), true)); //Tie listener
        rBtnPanel.add(btn);

        count++; //Go to next col
      }

      btmPanel.add(rBtnPanel, BorderLayout.NORTH);

      JPanel inputPanel = new JPanel(new GridLayout(count, 1, 0, 10));
      JTextField inputs[] = new JTextField[count];
      // inputPanel.setBackground(new Color(184,207,229)); //0xB8CFE5

      //Creates inputs for each attribute
      int ind = 0;
      for (String attr : colNames) {
        inputPanel.add(new JLabel(attr.substring(0, 1).toUpperCase() + attr.substring(1))); //Attr label
        inputs[ind] = new JTextField(10);

        inputPanel.add(inputs[ind]);
        ind++;
      }

      // ********************** UNDO FOR INPUT PANEL **********************
      // btmPanel.add(inputPanel, BorderLayout.CENTER);

      JPanel btnPanel = new JPanel();
      // btnPanel.setBackground(new Color(184,207,229)); //0xB8CFE5

      //Creates search button
      JButton searchBtn = new JButton("Search");

      searchBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            boolean typed = false;
            String sql = "SELECT * FROM " + name + " WHERE ";

            int i = 0;
            for (String attr : colNames) {
              if (typed && !inputs[i].getText().isEmpty()) {
                sql += " AND " + attr + " LIKE '%" + inputs[i].getText() + "%'";
              } else if (!inputs[i].getText().isEmpty()) {
                sql += attr + " LIKE '%" + inputs[i].getText() + "%'";
                typed = true;
              }
              i++;
            }

            //If an input was typed
            if (typed) {
              DefaultTableModel result = dao.getTableFromQuery(sql);
              JTable table = new JTable(result) {
                public Dimension getPreferredScrollableViewportSize() {
                  return new Dimension(500, 150);
                }
              };

              //Show result
              JOptionPane.showMessageDialog(mainPane, new JScrollPane(table), "Results", JOptionPane.INFORMATION_MESSAGE);
            }
          } catch (MallSqlException sqle) {
            JOptionPane.showMessageDialog(mainPane, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      });

      //Creates insert button
      //TODO
      JButton insertBtn = new JButton("Insert");

      insertBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            boolean typed = false;
            String sql = "INSERT INTO " + name + " VALUES (";

            int i = 0;
            for (String attr : colNames) {
              if (typed && !inputs[i].getText().isEmpty()) {
                sql += ", " + inputs[i].getText();
              } else if (!inputs[i].getText().isEmpty()) {
                sql += inputs[i].getText();
                typed = true;
              }
              i++;
            }

            sql += ")";

            //If an input was typed
            if (typed) {
              dao.updateQuery(sql);

              //Show success
              JOptionPane.showMessageDialog(mainPane, "Row added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
          } catch (MallSqlException sqle) {
            JOptionPane.showMessageDialog(mainPane, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      });

      //Creates insert button
      //TODO test
      JButton deleteBtn = new JButton("Delete");

      deleteBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            boolean typed = false;
            String sql = "DELETE FROM " + name + " WHERE ";

            int i = 0;
            for (String attr : colNames) {
              if (typed && !inputs[i].getText().isEmpty()) {
                sql += " AND " + attr + " LIKE '%" + inputs[i].getText() + "%'";
              } else if (!inputs[i].getText().isEmpty()) {
                sql += attr + " LIKE '%" + inputs[i].getText() + "%'";
                typed = true;
              }
              i++;
            }

            //If an input was typed
            if (typed) {
              //Confirm deletion
              Object[] options = {"DELETE", "CANCEL"};
              int confirm = JOptionPane.showOptionDialog(null, "Are you sure you want to delete data?", "Warning",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[0]);

              if (confirm == 0) { //Deletion confirmed
                int rowsDeleted = dao.updateQuery(sql);

                if (rowsDeleted != 0) { //Show success
                  JOptionPane.showMessageDialog(mainPane, "Row deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else { //Show failure
                  JOptionPane.showMessageDialog(mainPane, "No matching data found.", "Failure", JOptionPane.ERROR_MESSAGE);
                }
              }
            }
          } catch (MallSqlException sqle) {
            JOptionPane.showMessageDialog(mainPane, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      });


      //********************** UNDO FOR UPDATE BUTTONS **********************
      // btnPanel.add(searchBtn);
      // btnPanel.add(insertBtn);
      // btnPanel.add(deleteBtn);
      //
      // btmPanel.add(btnPanel, BorderLayout.SOUTH);

      panel.add(btmPanel);

      tabbedPane.addTab(name.substring(0, 1).toUpperCase() + name.substring(1), panel);
    }


    mainPane.add(tabbedPane, BorderLayout.CENTER); //Add tabbedpane to the mall frame
  }

  /**
    * This function sets up a footer which contains the date and time that the
    * database was connected.
    */
  public void setupFooter() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();

    JLabel footer = new JLabel("Mall connection established " + dateFormat.format(date));
    footer.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));

    mainPane.add(footer, BorderLayout.SOUTH);
  }
}
