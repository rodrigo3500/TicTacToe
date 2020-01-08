import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.applet.Applet;
import java.applet.AudioClip;
/**
 * Write a description of class Button here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Button extends JButton implements ActionListener{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 3246365430353383672L;
	
	ImageIcon X,O;
    int value=0;
    int row;
    int col;

    AudioClip click;

    public Button(int newRow, int newCol){  
        row = newRow;
        col = newCol;
        X=new ImageIcon(this.getClass().getResource("X.png"));
        O=new ImageIcon(this.getClass().getResource("O.png"));
        this.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){

        //Check if X players turn
        if(TicTacToe.getPlayer() == TicTacToe.PLAYER_X&&TicTacToe.getWinner().equals(" ")&&TicTacToe.getBoard()[row][col].equals(" ")){

            TicTacToe.setText("Player " + TicTacToe.PLAYER_O + "'s turn.");
            setIcon(X); //update the game interface
            URL urlClick = TicTacToe.class.getResource("hit.wav"); // beep
            click = Applet.newAudioClip(urlClick);
            click.play(); // just plays clip once
        }

        //Check if O players turn
        if(TicTacToe.getPlayer() == TicTacToe.PLAYER_O&&TicTacToe.getWinner().equals(" ")&&TicTacToe.getBoard()[row][col].equals(" ")){
    
            TicTacToe.setText("Player " + TicTacToe.PLAYER_X + "'s turn.");
            setIcon(O); //update the game interface
            URL urlClick = TicTacToe.class.getResource("hit.wav"); // beep
            click = Applet.newAudioClip(urlClick);
            click.play(); // just plays clip once
        }

        // Update the game board
        TicTacToe.makeMove(row,col);
    }

    public void resetValue(){
        value = 0;
        setIcon(null);
    }
}