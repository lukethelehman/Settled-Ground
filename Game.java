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
				boolean coin = flipCoin(.5);
				if (coin == true){
					System.out.println("flipping coin...");
					System.out.println(p2.name + " goes first");
					first = p2;
					second = p1;
				}
				else{
					System.out.println("flipping coin...");
					System.out.println(p1.name + " goes first");
				}

				while (true){
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
		System.out.println("\n=======================");
 	  	System.out.println("       GAME RULES");
   		System.out.println("=======================\n");

   		System.out.println(" GOAL:");
   		System.out.println("- Be the first player to reach 25 survival points.\n");
	
   		System.out.println(" ON EACH TURN:");
   		System.out.println("- Roll a 6-sided die to collect a resource.");
   		System.out.println("- After rolling, you may choose ONE action:\n");
   		System.out.println("  0.) Continue   → Do nothing this turn.");
   		System.out.println("  1.) Stash      → Convert 1 resource into 1 survival point.");
   		System.out.println("  2.) Barter     → Trade 1 Barter Token for resources.");
   		System.out.println("  3.) Build      → Spend resources to construct a structure for points.");
   		System.out.println("  4.) Rules      → View this menu again.");
   		System.out.println("  5.) Exit Game  → Return to the main menu.\n");
	
   		System.out.println(" DIE ROLL VALUES:");
   		System.out.println("  1 → Bandit encounter (25% chance of full resource loss)");
	   	System.out.println("  2 → Wood");
		System.out.println("  3 → Stone");
   		System.out.println("  4 → Food");
   		System.out.println("  5 → Water");
   		System.out.println("  6 → Barter Token\n");
	
	   	System.out.println(" STASHING:");
   		System.out.println("- You may stash 1 unit of Wood, Stone, Food, or Water.");
   		System.out.println("- Stashing gives you 1 survival point.");
		System.out.println("- You cannot stash Barter Tokens.\n");
	
	   	System.out.println(" BARTERING:");
   		System.out.println("- Trade 1 Barter Token for 1 resource of your choice.");
   		System.out.println("- If you own an Outpost, each Barter Token is worth 2 resources.\n");
	
	   	System.out.println(" STRUCTURES (Build Costs & Points):");
   		System.out.println("  1.) Fire Pit   → 1 Wood, 1 Stone           = 3 points");
   		System.out.println("  2.) Shelter    → 1 Wood, 1 Food, 1 Water   = 5 points");
   		System.out.println("  3.) Cabin      → 2 Wood, 2 Stone, 1 Food, 1 Water = 18 points");
  	 	System.out.println("  4.) Outpost    → 1 of each + 1 Barter Token = 10 points");
   		System.out.println("     → Having an Outpost doubles your bartering power.\n");
	
  	 	System.out.println(" INVENTORY:");
 		System.out.println("- Your inventory includes resources, structures you've built, and your points.");
		System.out.println("- You can view your inventory after every roll.\n");

   		System.out.println(" WINNING:");
   		System.out.println("- First player to reach 25 points wins the game.\n");
	
		System.out.println("Have fun!\n");	
	}
	
	public void selectPlayers(){
		System.out.print("Player 1 name: ");
		String player1Name = input.nextLine();
		p1 = new Player(player1Name);
		System.out.print("Player 2 name: ");
		String player2Name = input.nextLine();
		p2 = new Player(player2Name);
		
	}
	public boolean flipCoin(double chanceTrue){
		double toss = rand.nextDouble();
		if (toss < chanceTrue){
			return true;
		}
		else {
			return false;
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
		System.out.println("4.) View Inventory");
		System.out.println("5.) Rules/Instructions");
		System.out.println("6.) Exit Game");
		System.out.print("Please Enter (0-6): ");
		int response = p1.getInt(0,6);
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
			boolean coin = flipCoin(.75);
			System.out.println(" and encounters the bandit!!!");
			if (coin == false){
				System.out.println("The bandit steals all of " + player.name + "'s resources");
				player.clearResources();
			}
			else{
				System.out.println(player.name + " narrowly escapes with their resources");
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
				player.printInventory();
			}
			else if (response == 5){
				printRules();
			}
			else if (response == 6){
				return false;
			}

		}
		System.out.println("-------------------------------");
		return true;
	}
}


		

