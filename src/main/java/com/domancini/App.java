package com.domancini;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {
            System.out.println(UIManager.getLookAndFeel());
        }
        SwingUtilities.invokeLater(TimerGui::new);

    }
}
