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
		System.out.println("=======================================");
		System.out.println("      WELCOME TO SETTLED GROUND");
		System.out.println("=======================================");

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
				System.out.println();
				selectPlayers();
				System.out.println("Welcome, " + p1.name + " and " + p2.name);
				Player first = p1;
				Player second = p2;
				System.out.println();
				System.out.print("Press ENTER to flip coin and see who goes first: ");
				input.nextLine();
				String coin = flipCoin(.5);
				if (coin.equals("heads")){
					System.out.println("flipping coin...");
					System.out.println(p2.name + " goes first");
					first = p2;
					second = p1;
				}
				else{
					System.out.println("flipping coin...");
					System.out.println(p1.name + " goes first");
				}

				boolean inGame = true;
				while (inGame){
					if (takeTurn(first) == false){
						break;
					}
					if (first.inventory[Items.POINTS] >= 25){
						System.out.println(first.name + " wins!!!");
						break;
					}
					if (takeTurn(second) == false){
						break;
					}
					if (second.inventory[Items.POINTS] >= 25){
						System.out.println(second.name + " wins!!!");	
						break;
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
		System.out.println();
		System.out.println("-- Action Menu --");
		System.out.println("0.) Continue");
		System.out.println("1.) Stash");
		System.out.println("2.) Barter");
		System.out.println("3.) Build");
		System.out.println("4.) Rules/Instructions");
		System.out.println("5.) Exit Game");
		System.out.print("Please Enter (0-5): ");
		int response = p1.getInt(0,5);
		return response;
	}

	public boolean takeTurn(Player player){
		int roll = rollDie();
		System.out.println();
		System.out.println("===============================");
		System.out.println(player.name + "'s Turn");
		System.out.println("===============================");
		System.out.print("Press ENTER to roll the die: ");
		input.nextLine();
		System.out.println();
		System.out.print(player.name + " rolls a... " + roll);
		
		if (roll == 1){
			String coin = flipCoin(.66);
			System.out.println(" and encounters the bandit!!!");
			if (coin.equals("tails")){
				System.out.println("The bandit steals all your resources");
				player.clearResources();
			}
			else{
				System.out.println("You narrowly escape with your resources");
			}
		}
		else if (roll == 2){
			player.addResource(Items.WOOD);
			System.out.println(" and collects 1 Wood");
		}
		else if (roll == 3){
			player.addResource(Items.STONE);
			System.out.println(" and collects 1 Stone");
		}
		else if (roll == 4){
			player.addResource(Items.FOOD);
			System.out.println(" and collects 1 Food");
		}
		else if (roll == 5){
			player.addResource(Items.WATER);
			System.out.println(" and collects 1 Water");
		}
		else if (roll == 6){
			player.addResource(Items.BARTERTOKEN);
			System.out.println(" and collects 1 Barter Token");
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
				return false;
			}

		}
		System.out.println("-------------------------------");
		return true;
	}
}


		

