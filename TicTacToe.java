import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.io.*;
import javax.sound.sampled.*;
import java.awt.event.*;

/**
 * A class modelling a tic-tac-toe (noughts and crosses, Xs and Os) game.
 *
 * @author Rodrigo Fierro
 * @version November 22, 2019
 */


public class TicTacToe extends JFrame{

  
	/**
	 * 
	 */
	private static final long serialVersionUID = 2220122022487251404L;
	
	// FRONT END GUI Variables
    private static JPanel panel;// panel holding the game buttons
    private static Button buttons[][];// 3x3 array containing the board buttons
    private static JLabel label;
    private static Toolbar toolbar;

    // RESOLUTION
    public static final int FONT_SIZE = 35;
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = SCREEN_WIDTH*8/6;

    // GAME  variables
    public static final String PLAYER_X = "X"; // player using "X"
    public static final String PLAYER_O = "O"; // player using "O"
    public static final String EMPTY = " ";  // empty cell
    public static final String TIE = "nobody, we have a tie!"; // game ended in a tie

    // highscores stats
    private static String gameScores;
    private static int xWins;
    private static int oWins;
    private static int tieGames;

    // BACK END game logic variables 
    Scanner sc; // for text based gameplay
    private static String player;   // current player (PLAYER_X or PLAYER_O)
    private static String winner;   // winner: PLAYER_X, PLAYER_O, TIE, EMPTY = in progress
    private static int numFreeSquares; // number of squares still fre
    private static String board[][]; // 3x3 array representing the board

    //Audio 
    private static AudioClip click;
    private static Clip clip;

    // The reset  menu button
    private JMenuItem newGame;

    // The quit menu item 
    private JMenuItem quit;

    public TicTacToe(){
        super("TicTacToe");

        // GUI objects
        toolbar = new Toolbar();
        label = new JLabel("Welcome to Tic Tac Toe",SwingConstants.CENTER);
        buttons = new Button[3][3];
        board = new String[3][3];

        // Pick a new font size 
        label.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));

        //window specs
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH,SCREEN_HEIGHT);

        // buttons grid
        panel = new JPanel();
        panel.setLayout(new GridLayout(3,3,5,5));

        // Create the buttons 
        for(int row=0;row<3;row++){
            for(int col=0;col<3;col++){
                buttons[row][col]=new Button(row,col);
                panel.add(buttons[row][col]);
            }
        }

        // add to the layout
        add(label,BorderLayout.SOUTH);
        add(toolbar,BorderLayout.NORTH);
        add(panel,BorderLayout.CENTER);

        // Highscores page variables
        xWins = 0;
        oWins =0;
        tieGames = 0;
        generateScoresString();// this method initializes the gameScores string 

        /* Create JMenu */
        JMenuBar menu = new JMenuBar();
        super.setJMenuBar(menu); 

        JMenu fileMenu = new JMenu("Options"); // create a menu
        menu.add(fileMenu); // and add to our menu bar

        newGame = new JMenuItem("NewGame"); // create a menu item called "Reset"
        fileMenu.add(newGame); // and add to our menu

        quit = new JMenuItem("Quit"); // create a menu item called "Quit"
        fileMenu.add(quit); // and add to our menu

        // this allows us to use shortcuts (e.g. Ctrl-N and Ctrl-Q)
        final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(); // to save typing
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));

        // listen for menu selections
        newGame.addActionListener(new ActionListener() // create an anonymous inner class
            { // start of anonymous subclass of ActionListener
                // this allows us to put the code for this action here  
                public void actionPerformed(ActionEvent event){
                    clearBoard();
                }
            } // end of anonymous subclass
        ); // end of addActionListener parameter list and statement

        quit.addActionListener(new ActionListener() // create an anonymous inner class
            { // start of anonymous subclass of ActionListener
                // this allows us to put the code for this action here  
                public void actionPerformed(ActionEvent event)
                {
                    System.exit(0); // quit
                }
            } // end of anonymous subclass
        ); // end of addActionListener parameter list and statement

        //super.pack();--> did not do this because it makes it too small
        setVisible(true);
        playGame();
    }

    /**
     * Sets everything up for a new game.  Marks all squares in the Tic Tac Toe board as empty,
     * and indicates no winner yet, 9 free squares and the current player is player X.
     */
    public static void clearBoard()
    {
        for (int row = 0; row < 3; row ++) {
            for (int col  = 0; col < 3; col ++) {
                board[row][col] = EMPTY;
                buttons[row][col].resetValue();
            }
        }

        winner = EMPTY;
        numFreeSquares = 9;
        player = PLAYER_X;     // Player X always has the first turn.
        TicTacToe.setText("Player " + PLAYER_X + "'s turn.");
    }

    /**
     * Plays one game of Tic Tac Toe.
     */

    public void playGame()
    {
        clearBoard(); // clear the board
        makeString(); // make the game array

    }

    /**
     * Returns true if filling the given square gives us a winner, and false
     * otherwise.
     *
     * @param int row of square just set
     * @param int col of square just set
     * 
     * @return true if we have a winner, false otherwise
     */
    private static boolean haveWinner(int row, int col) 
    {
        // unless at least 5 squares have been filled, we don't need to go any further
        // (the earliest we can have a winner is after player X's 3rd move).

        if (numFreeSquares>4) return false;

        // Note: We don't need to check all rows, columns, and diagonals, only those
        // that contain the latest filled square.  We know that we have a winner 
        // if all 3 squares are the same, as they can't all be blank (as the latest
        // filled square is one of them).

        // check row "row"
        if ( board[row][0].equals(board[row][1]) &&
        board[row][0].equals(board[row][2]) ) return true;

        // check column "col"
        if ( board[0][col].equals(board[1][col]) &&
        board[0][col].equals(board[2][col]) ) return true;

        // if row=col check one diagonal
        if (row==col)
            if ( board[0][0].equals(board[1][1]) &&
            board[0][0].equals(board[2][2]) ) return true;

        // if row=2-col check other diagonal
        if (row==2-col)
            if ( board[0][2].equals(board[1][1]) &&
            board[0][2].equals(board[2][0]) ) return true;

        // no winner yet
        return false;
    }

    

    /**
     * Returns a string representing the current state of the game.  This should look like
     * a regular tic tac toe board, and be followed by a message if the game is over that says
     * who won (or indicates a tie).
     *
     * @return String representing the tic tac toe game state
     */
    public static String makeString()
    {
        String boardString = "";

        for (int i = 0 ; i < 3; i++){

            for (int j = 0;j < 3 ; j ++){
                boardString += board[i][j];

                if(j != 2){
                    boardString += " | "; 
                }
                if(j == 2){
                    boardString += "\n";
                }

            }
            if( i != 2){
                boardString += ("----------\n");
            }

        }
        if(!winner.equals(" ")){
            boardString += "Winner is " + winner;
            label.setText("Winner is " + winner);

            // update the winner history
            if(winner.equals(PLAYER_X)){
                xWins += 1;
            }
            if(winner.equals(PLAYER_O))
            {
                oWins +=1 ;
            }
            if(winner.equals(TIE))
            {
                tieGames +=1 ;
            }
            generateScoresString();// update the gamescores string

            // play game over music

            // Import Sounds
            try {
                URL url = TicTacToe.class.getResource("gameover.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                // Get a sound clip resource.
                clip = AudioSystem.getClip();
                // Open audio clip and load samples from the audio input stream.
                clip.open(audioIn);  
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            clip.start();

        }
        return boardString; 
    }

    /**
     * Updates the back end string array with a new move. 
     * Requires the coordinates of the board to play 
     * a move.
     *
     * @param int row to determine the row on the board to make a move
     * @param int col to determine the row on the board to make a move
     */
    public static void makeMove(int row, int col){
        // check if the game has a winner 
        if(!winner.equals(" ")){
            return;
        }
        // check is the spot is already used
        if (board[row][col]!= EMPTY){
            label.setText("Please make your move somewhere else.");
            URL urlClick = TicTacToe.class.getResource("wrong.wav"); // beep
            click = Applet.newAudioClip(urlClick);
            click.play(); // just plays clip once
            return;
        }

        board[row][col] = player;        // fill in the square with player
        numFreeSquares--;            // decrement number of free squares

        // see if the game is over
        if (haveWinner(row,col)) 
            winner = player; // must be the player who just went
        else if (numFreeSquares==0) 
            winner = TIE; // board is full so it's a tie    

        // change to other player (this won't do anything if game has ended)
        if (player==PLAYER_X) 
            player=PLAYER_O;
        else 
            player=PLAYER_X;

        // print current board
        makeString();

    }

    /**
     * Update the highscores string.
     * 
     */

    public static void generateScoresString(){
        gameScores ="<html><h1>TIC TAC TOE SCORES</h1>"+
        "<h2>X player wins: "+ xWins + "</h2>" +
        "<h2>O player wins: "  + oWins + " </h2> " +
        "<h2>Tied games: "  + tieGames + " </h2> " +
        "</html>";
    }

    /**
     * Retuns the string of the player whos turn it is.
     *
     * @return a String of whichever players turn it is. 
     */
    public static String getPlayer(){
        return player;
    }

    /**
     * Retuns the winner string. Empty if game is still in progress
     *
     * @return a String containing the current winner status
     */
    public static String getWinner(){
        return winner;
    }

    /**
     * Updates the Jtextarea with a string.
     *
     * @param String to set the text box
     */
    public static void setText(String input){
        label.setText(input);
    }

    /**
     * Return the game board array
     * 
     * @return String[][] array of the current board
     */
    public static String[][] getBoard(){
        return board;
    }

    /**
     * Return the highscore string
     * 
     * @return String the current scores string.
     */
    public static String  getHighscoreString(){
        return gameScores;
    }

}