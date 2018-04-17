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
import java.util.Comparator;

/**
  * This class defines the Mall SQL Assistant.
  *
  * @author Mikayla Crawford
  */
public class MallFrame extends JFrame {
  private final int WIDTH = 800;
  private final int HEIGHT = 640;
  private MallDAO dao;
  private JPanel mainPane;
  private JTable[] tables;

  /**
    * Default constructor for the Mall GUI.
    */
  MallFrame() {
    dao = new MallDAO(); //Intializes the DOA
    mainPane = new JPanel();
    tables = new JTable[8];

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
          try {
            if (sql.toLowerCase().contains("select")) { //Query
              DefaultTableModel result = dao.getTableFromQuery(sql);
              JTable table = new JTable(result) {
                public Dimension getPreferredScrollableViewportSize() {
                  return new Dimension(500, 150);
                }
              };

              //Show result
              JOptionPane.showMessageDialog(mainPane, new JScrollPane(table), "Query Result", JOptionPane.INFORMATION_MESSAGE);
            } else { //Update
              int rowsAffected = dao.updateQuery(sql);
              List<String> tabNames = dao.getTableNames(); //Gets all the relation names

              //Updates all tables since you don't know what the query was
              int i = 0;
              for (String relation : tabNames) {
                DefaultTableModel results = dao.getTableFromQuery("SELECT * FROM " + relation);

                tables[i].setModel(results);
                i++;
              }

              JOptionPane.showMessageDialog(mainPane, "Update was made successfully. " + rowsAffected + " row(s) affected.", "Update Success", JOptionPane.INFORMATION_MESSAGE);
            }
          } catch(MallSqlException mSql) {
            JOptionPane.showMessageDialog(mainPane, mSql.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
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
    JScrollPane scrollPane; //Blank scroll pane to add table to

    //Creates tab for each relation
    int i = 0;
    for (String name : tabNames) {
      panel = new JPanel(); //Needed since a component can only be added once
      panel.setSize(750, 300);

      btmPanel = new JPanel(new BorderLayout(45, 45));

      //Creates table for each relation
      tables[i] = new JTable(dao.getTuples(name)) {
        @Override
        public Dimension getPreferredScrollableViewportSize() {
          return new Dimension(750, 100);
        }

        @Override
        public boolean isCellEditable(int row, int column){
          return false;
        }

        @Override
        public Class<?> getColumnClass(int column) {
          String name = this.getColumnName(column);

          if (name.contains("id") || name.contains("quantity")) {
            return Integer.class;
          } else if (name.contains("salary") || name.contains("balance") || name.contains("price")) {
            return java.math.BigDecimal.class;
          } else {
            return String.class;
          }
        }
      };

      tables[i].setFont(new Font("Calibri", Font.PLAIN, 15)); //Sets font for table
      tables[i].setAutoCreateRowSorter(true);
      tables[i].setRowSelectionAllowed(false);

      //Defines sorter for tables, used to properly differentiate between numeric and string types
      TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tables[i].getModel());
      tables[i].setRowSorter(sorter);

      //Adds table to scrollPane (needed for attr names to be seen) and adds border
      scrollPane = new JScrollPane(tables[i]);
      scrollPane.setBorder(BorderFactory.createLineBorder(Color.black)); //Adds border to scroll pane
      panel.add(scrollPane);

      //Creating radio buttons for each attribute
      List<String> colNames = dao.getAllColumns(name); //Gets all the column names for the specific relation

      int columnCount = tables[i].getColumnModel().getColumnCount();
      JPanel rBtnPanel = new JPanel();
      JPanel inputPanel = new JPanel(new GridLayout(columnCount, 3, 25, 10));
      JTextField inputs[] = new JTextField[columnCount];
      JComboBox dropdowns[] = new JComboBox[columnCount];
      String eqlsVals[] = new String[]{"=", "<", ">"};

      //Creates radio buttons and input for each attribute
      int count = 0; //Tracks index of curr column
      for (String attr : colNames) {
        //Create and add button initliazed to selected
        JRadioButton btn = new JRadioButton(attr);
        btn.setSelected(true);
        btn.addActionListener(new ColBtnAction(tables[i], tables[i].getColumnModel().getColumn(count), true)); //Tie listener
        rBtnPanel.add(btn);

        //Creates and adds inputs with labels and dropdowns
        JPanel comboPanel = new JPanel();
        inputPanel.add(new JLabel(attr.substring(0, 1).toUpperCase() + attr.substring(1))); //Attr label
        dropdowns[count] = new JComboBox(eqlsVals);
        inputPanel.add(dropdowns[count]);
        inputs[count] = new JTextField(10);
        inputPanel.add(inputs[count]);

        //Makes numeric types sorted as numerics not alphabetical
        Class cls = tables[i].getColumnClass(count);

        if (Integer.class.isAssignableFrom(cls)) {
          sorter.setComparator(count, new IntComparator());
        } else if(java.math.BigDecimal.class.isAssignableFrom(cls)) {
          sorter.setComparator(count, new DblComparator());
        }

        count++; //Go to next col
      }

      btmPanel.add(rBtnPanel, BorderLayout.NORTH);
      btmPanel.add(inputPanel, BorderLayout.CENTER);

      JPanel btnPanel = new JPanel();

      //Creates search button
      JButton searchBtn = new JButton("Search");
      searchBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            boolean typed = false;
            String sql = "SELECT * FROM " + name + " WHERE ";

            //Builds WHERE clause
            int i = 0;
            for (String attr : colNames) {
              Boolean stringAttr = String.class.isAssignableFrom(tables[tabbedPane.getSelectedIndex()].getColumnClass(i));

              if (typed && !inputs[i].getText().isEmpty()) {
                sql += " AND " + attr + dropdowns[i].getSelectedItem() + (stringAttr ? "'" : "") + inputs[i].getText() + (stringAttr ? "'" : "");
              } else if (!inputs[i].getText().isEmpty()) {
                sql += attr + dropdowns[i].getSelectedItem() + (stringAttr ? "'" : "") + inputs[i].getText() + (stringAttr ? "'" : "");
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
            JOptionPane.showMessageDialog(mainPane, "Unfortunately, this search cannot be made.", "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      });

      //Creates update button
      JButton updateBtn = new JButton("Update");
      updateBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            boolean typed = false;
            List<String> colNames = dao.getAllColumns(name); //Gets all the column names for the specific relation
            String endSql = "";

            //Builds WHERE clause
            int i = 0;
            for (String attr : colNames) {
              Boolean stringAttr = String.class.isAssignableFrom(tables[tabbedPane.getSelectedIndex()].getColumnClass(i));

              if (typed && !inputs[i].getText().isEmpty()) {
                endSql += " AND " + attr + dropdowns[i].getSelectedItem() + (stringAttr ? "'" : "") + inputs[i].getText() + (stringAttr ? "'" : "");
              } else if (!inputs[i].getText().isEmpty()) {
                endSql += attr + dropdowns[i].getSelectedItem() + (stringAttr ? "'" : "") + inputs[i].getText() + (stringAttr ? "'" : "");
                typed = true;
              }

              i++;
            }

            //If input was typed
            if (typed) {
              String[] colNameArr = new String[colNames.size()];
              String[] eqlArr = new String[]{"=", "!=", "<", ">"};

              //Move from list to array
              int j = 0;
              for (String colName : colNames) {
                colNameArr[j] = colName;
                j++;
              }

              //Create update popup
              JPanel updatePanel = new JPanel();
              JComboBox colDropDown = new JComboBox(colNameArr);
              JTextField newInput = new JTextField(5);

              updatePanel.add(new JLabel("Update"));
              updatePanel.add(colDropDown);
              updatePanel.add(new JLabel("to"));
              updatePanel.add(newInput);

              JComponent[] options = new JComponent[] {updatePanel};

              int result = JOptionPane.showConfirmDialog(mainPane, options, "Update", JOptionPane.PLAIN_MESSAGE);

              //If an new value was typed and user presses ok
              if (result == 0 && !newInput.getText().isEmpty()) {
                String col = (String)colDropDown.getSelectedItem();
                String sql = "UPDATE " + name + " SET " + col + " = '" + newInput.getText() + "' WHERE " + endSql;

                int rowsAffected = dao.updateQuery(sql);
                DefaultTableModel results = dao.getTableFromQuery("SELECT * FROM " + name);
                tables[tabbedPane.getSelectedIndex()].setModel(results);

                //Show success
                JOptionPane.showMessageDialog(mainPane, "Update was made successfully. " + rowsAffected + " row(s) affected.", "Update Success", JOptionPane.INFORMATION_MESSAGE);
              }
            }
          } catch (MallSqlException sqle) {
            JOptionPane.showMessageDialog(mainPane, "Unfortunately, this update cannot be made.", "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      });

      btnPanel.add(searchBtn);
      btnPanel.add(updateBtn);

      btmPanel.add(btnPanel, BorderLayout.SOUTH);

      panel.add(btmPanel);

      tabbedPane.addTab(name.substring(0, 1).toUpperCase() + name.substring(1), panel);
      i++;
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

  /**
    * This class defines a comparator for an integer type. It is meant to be
    * used for all ids and quantity types.
    */
  class IntComparator implements Comparator {
    public int compare(Object o1, Object o2) {
      Integer int1 = (Integer)o1;
      Integer int2 = (Integer)o2;
      return int1.compareTo(int2);
    }

    public boolean equals(Object o2) {
      return this.equals(o2);
    }
  }

  /**
    * This class defines a comparator for a java.lang.BigDecimal type. It is meant to be used
    * for monetary types (salary, balance, and price).
    */
  class DblComparator implements Comparator {
    public int compare(Object o1, Object o2) {
      java.math.BigDecimal int1 = (java.math.BigDecimal)o1;
      java.math.BigDecimal int2 = (java.math.BigDecimal)o2;
      return int1.compareTo(int2);
    }

    public boolean equals(Object o2) {
      return this.equals(o2);
    }
  }
}
