
public class Main {

	private static TicTacToe game;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		setGame(new TicTacToe());
		game = new TicTacToe();
		
	}
	
	public Main () {
		game = new TicTacToe();
	}

	public static TicTacToe getGame() {
		return game;
	}

	public static void setGame(TicTacToe game) {
		Main.game = game;
	}
	
	

}
