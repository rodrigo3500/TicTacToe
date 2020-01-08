import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Toolbar for the Tic Tac Toe Game. Contains buttons to 
 * create new game (or Cntrl-N) and quit game (or Ctrl-O).
 *
 * @author Rodrio Fierro
 * @version version 1
 */
public class Toolbar extends JPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2554169274148939112L;
	private JButton newGame;
    private JButton quit;
    private JButton highscores;

    /**
     * Constructor for objects of class Toolbar
     */
    public Toolbar()
    {
        newGame = new JButton("New Game (Ctrl + N)");
        quit = new JButton("Quit (Ctrl + Q)");
        highscores = new JButton("Highscores");

        newGame.addActionListener(new ActionListener()  {
                public void actionPerformed(ActionEvent arg0) {
                    TicTacToe.clearBoard();
                }
            });

        quit.addActionListener(new ActionListener()  {
                public void actionPerformed(ActionEvent arg0) {
                    System.exit(1);
                }
            });

        highscores.addActionListener(new ActionListener()  {
                public void actionPerformed(ActionEvent arg0) {
                    @SuppressWarnings("unused")
					HighscoresFrame high = new HighscoresFrame();
                }
            });


        setLayout(new FlowLayout());
        add(newGame);
        add(quit);
        add(highscores);

    }

}
