//Game.java

public class Game {
	Player p1;
	Player p2;
	java.util.Scanner input = new java.util.Scanner(System.in);

	public static void main(String[] args){
		Game g = new Game();
		g.start();
	}
	
	public Game(){
		p1 = new Player("Player1");
		p2 = new Player("Player2");
	}

	public void start(){
		boolean keepGoing = true;
		while (keepGoing){
			int response = gameMenu();
			if (response == 0){
				keepGoing = false;
			}
			else if (response == 1){
				printRules();
			}
			else if (response == 2){
				selectPlayers();
				Player first = p1;
				Player second = p2;
				int coin = flipCoin();
				if (coin == 1){
					first = p2;
					second = p1;
				}
				boolean noWinner = true;
				while (noWinner){
					takeTurn(first);
					if (first.inventory[Items.POINTS] >= 25){
						System.out.println(first.name + " wins!!!");
						noWinner = false;
					}
					takeTurn(second);
					if (second.inventory[Items.POINTS] >= 25){
						System.out.println(second.name + " wins!!!");
						noWinner = false;
					}
				}
			}
		}
	}

	public int gameMenu(){
		System.out.println("0.) Exit");
		System.out.println("1.) Rules/Instructions");
		System.out.println("2.) Start game");
		System.out.print("Please Enter (0-2): ");
		int response = p1.getInt(0,2);
		return response;
	}

	public void printRules(){
		System.out.println("Rules/Instructions");
	}

	public void selectPlayers(){
		System.out.print("Player 1 name: ");
		String player1Name = input.nextLine();
		p1 = new Player(player1Name);
		System.out.print("Player 2 name: ");
		String player2Name = input.nextLine();
		p2 = new Player(player2Name);
		
	}

	public int flipCoin(){
		return 0;
	}

	public void takeTurn(Player player){
		System.out.println(player.name);
	}
}


		

