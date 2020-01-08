import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * Displays highscores on a new frame.
 *
 * @author Rodrigo Fierro
 * @version 1
 */
public class HighscoresFrame implements ActionListener
{
    private JButton quit;
    private JLabel label;
    private  JFrame frame;
    /**
     * Constructor for objects of class HighscoresFrame
     */
    public HighscoresFrame()
    {
        // initialise instance variables

        // JFrame
        frame = new JFrame("Highscores");
        // Make content pane
        Container contentPane = frame.getContentPane(); 
        contentPane.setLayout(new BorderLayout()); // use border layout (default)

        frame.setResizable(false);
        frame.setDefaultCloseOperation(TicTacToe.EXIT_ON_CLOSE);
        frame.setSize(TicTacToe.SCREEN_WIDTH/2,TicTacToe.SCREEN_HEIGHT/2);

        // Make label
        label = new JLabel(TicTacToe.getHighscoreString(),SwingConstants.CENTER);
        contentPane.add(label);

        // Add button
        quit = new JButton("Close");
        frame.add(quit,BorderLayout.SOUTH);
        quit.addActionListener(this);

        //frame.pack(); makes frame too small, won't use
        frame.setVisible(true); // it's visible

    }

    /** This action listener is called when the user clicks on 
     * any of the GUI's buttons. 
     */
    public void actionPerformed(ActionEvent e)
    {
        frame.dispose();
    }    

    

}
