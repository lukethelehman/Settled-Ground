//Game.java
import java.util.Random;

public class Game {
	Player p1;
	Player p2;
	java.util.Scanner input = new java.util.Scanner(System.in);
	Random rand = new Random();
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
				String coin = flipCoin(.5);
				if (coin == "heads"){
					System.out.println("flipping coin, heads goes first...");
					System.out.println(p2.name + " lands heads and goes first");
					first = p2;
					second = p1;
				}
				else{
					System.out.println("flipping coin, heads goes first...");
					System.out.println(p1.name + " lands heads and goes first");
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
		System.out.println();
		System.out.println("Game Menu");
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
	public String flipCoin(double chanceOfHeads){
		double toss = rand.nextDouble();
		if (toss < chanceOfHeads){
			return "heads";
		}
		else {
			return "tails";
		}
	}

	public int rollDie(){
		int roll = rand.nextInt(6) + 1;
		return roll;
	}

	public int actionMenu(){
		System.out.println("0.) Continue");
		System.out.println("1.) Stash");
		System.out.println("2.) Barter");
		System.out.println("3.) Build");
		System.out.println("4.) Rules/Instructions");
		System.out.println("5.) Exit Game");
		int response = p1.getInt(0,5);
		return response;
	}

	public void takeTurn(Player player){
		int roll = rollDie();
		System.out.println(player.name + " rolls a... " + roll);
		
		if (roll == 1){
			String coin = flipCoin(.5);
			System.out.println("and encounters the bandit!!! If they flip tails they lose all their resources");
			System.out.println(player.name + "flips a coin and lands " + coin);
			if (coin == "tails"){
				System.out.println("The bandit steals all your resources");
				player.clearResources();
			}
			else{
				System.out.println("You narrowly escape with your resources");
			}
		}
		else if (roll == 2){
			player.addResource(Items.WOOD);
			System.out.println("and collects 1 Wood");
		}
		else if (roll == 3){
			player.addResource(Items.STONE);
			System.out.println("and collects 1 STONE");
		}
		else if (roll == 4){
			player.addResource(Items.FOOD);
			System.out.println("and collects 1 Food");
		}
		else if (roll == 5){
			player.addResource(Items.WATER);
			System.out.println("and collects 1 Water");
		}
		else if (roll == 6){
			player.addResource(Items.BARTERTOKEN);
			System.out.println("and collects 1 Barter Token");
		}
		
		player.printInventory();
		
		boolean keepGoing = true;
		while (keepGoing){
			int response = actionMenu();
			if (response == 0){
				keepGoing = false;
			}
			else if (response == 1){
				if (player.stashResource()){
					keepGoing = false;
				}
			}
			else if (response == 2){
				if (player.useBarter()){
					keepGoing = false;
				}
			}
			else if (response == 3){
				if (player.build()){
					keepGoing = false;
				}
			}
			else if (response == 4){
				printRules();
			}
			else if (response == 5){
				start();
			}
		}
	}							
}


		

