//Player.java

public class Player {
	int[] inventory = new int[10];
	String name;
	java.util.Scanner input = new java.util.Scanner(System.in);

	Player(String name){
		this.name = name;
	}

/*	public static void main(String args[]) {
		Player p = new Player("lucas");
		p.addResource(Items.WOOD);
		p.addResource(Items.WATER);
		p.addResource(Items.BARTERTOKEN);
		p.addResource(Items.FOOD);
		p.addResource(Items.STONE);
		p.printInventory();
		p.build();
		p.printInventory();
	} */

	public void printInventory(){
		System.out.println();
		System.out.println(name + "'s Inventory");
		System.out.println("   " + Items.itemStrings[Items.POINTS] + ": " + inventory[Items.POINTS]);
		for (int i = 1; i < inventory.length; i++){
			if (inventory[i] != 0){
				System.out.println("   " + Items.itemStrings[i] + ": " + inventory[i]);
			}
		}	
	}

	public void addResource(int resource){
		inventory[resource]++;
	}

	public boolean stashResource(){
		boolean keepGoing = true;
		while (keepGoing){
			System.out.println();
			System.out.println("Which resource would you like to stash?");
			System.out.println("1.) Wood");
			System.out.println("2.) Stone");
			System.out.println("3.) Food");
			System.out.println("4.) Water");
			System.out.println("5.) Exit");
			System.out.print("Please Enter (1-5): ");
			int response = getInt(1,5);
			if (response == 5){
				return false;
			}
			if (inventory[response] != 0){
				inventory[response]--;
				inventory[Items.POINTS] ++;
				System.out.println();
				System.out.print(name + " stashes one " + Items.itemStrings[response]);
				System.out.println(" to earn one point");
				return true;
			}
			else if (inventory[response] == 0) {
				System.out.println("ERROR: No resource to stash");
			}
		}
		return false;
	}

	public boolean useBarter(){
		int choices = 0;
		if (inventory[Items.BARTERTOKEN] <= 0){
			System.out.println();
			System.out.println("No Barter Tokens");
			return false;
		}
		else {
			inventory[Items.BARTERTOKEN]--;
			if (inventory[Items.OUTPOST] >= 1){
				choices = 2;
			}
			else {
				choices = 1;
			}
			for (int i = 0; i < choices; i++){
				System.out.println();
				System.out.println("Which resource would you like?");
				System.out.println("1.) Wood");
				System.out.println("2.) Stone");
				System.out.println("3.) Food");
				System.out.println("4.) Water");
				System.out.print("Please Enter (1-4): ");
				int response = getInt(1,4);
				inventory[response]++;
				System.out.println(name + " gains one " + Items.itemStrings[response]);
			}
			return true;
		}
	}
	public void clearResources(){
		for (int i = 1; i <= Items.BARTERTOKEN; i++){
			inventory[i] = 0;
		}
	}
	
	public boolean build(){
		boolean keepGoing = true;
		while (keepGoing) {
			System.out.println();
			System.out.println("Which structure would you like to build?");
			System.out.println("1.) Firepit");
			System.out.println("2.) Shelter");
			System.out.println("3.) Cabin");
			System.out.println("4.) Outpost");
			System.out.println("5.) Exit");
			System.out.print("Please Enter (1-5): ");
			int response = getInt(1,5);
			if (response == 5){
				return false;
			}
			int cost[] = new int[10];
			if (response == 1){
				cost = Items.costFirepit;
			}
			else if (response == 2){
				cost = Items.costShelter;
			}
			else if (response == 3) {
				cost = Items.costCabin;
			}
			else if (response == 4) {
				cost = Items.costOutpost;
			}

			if (canBuild(cost)){
				for (int i = 1; i <= Items.BARTERTOKEN; i++){
					inventory[i] = inventory[i] - cost[i];
				}
				inventory[Items.POINTS] = inventory[Items.POINTS] + cost[Items.POINTS];
				inventory[response + 5]++;
				System.out.println();
				System.out.print(name + " built a " + Items.itemStrings[response +5]);
				System.out.println(" for " + cost[Items.POINTS] + " points");
				return true;
			}
			else {
				System.out.println("Not enough resources");
			}
		}
		return false;
	}

	public boolean canBuild(int[] cost){
		for (int i = 1; i <= Items.BARTERTOKEN; i++){
			if (inventory[i] < cost[i]){
				return false;
			}
		}
		return true;
	}
		
			

	public int getInt(int lowBound, int upBound){
		boolean keepGoing = true;
		String response;
		int result = 0;
		while (keepGoing){
			response = input.nextLine();
			try {
				result = Integer.parseInt(response);
				if (result >= lowBound && result <= upBound){
					keepGoing = false;
				}
				else {
					System.out.print("ERROR: Must be between (" + lowBound + "-" + upBound + "): ");
				}
			} catch(NumberFormatException e) {
				System.out.print("ERROR: please enter an integer: ");
			}
		}
		return result;
	}
}
