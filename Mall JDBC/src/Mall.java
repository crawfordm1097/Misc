import javax.swing.JFrame;
import javax.swing.*;

/**
  * This class creates an instance of the Mall SQL Assistant. It serves to run
  * the application.
  *
  * @author Mikayla Crawford
  */
public class Mall {

  public static void main(String args[]) {
      SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        MallFrame mallFrame = new MallFrame();
        mallFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mallFrame.setVisible(true);
        mallFrame.setLocationRelativeTo(null); //Centers
    }
}
