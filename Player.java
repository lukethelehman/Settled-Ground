//Player.java

public class Player {
	int[] inventory = new int[10];
	String name;
	java.util.Scanner input = new java.util.Scanner(System.in);

	Player(String name){
		this.name = name;
	}

	public static void main(String args[]) {
		Player p = new Player("lucas");
		p.addResource(Items.WOOD);
		p.addResource(Items.WATER);
		p.addResource(Items.BARTERTOKEN);
		p.addResource(Items.OUTPOST);
		p.printInventory();
		p.clearResources();
		p.printInventory();
	}

	public void printInventory(){
		System.out.println(name + "'s Inventory");
		System.out.println(Items.itemStrings[Items.POINTS] + ": " + inventory[Items.POINTS]);
		for (int i = 1; i < inventory.length; i++){
			if (inventory[i] != 0){
				System.out.println(Items.itemStrings[i] + ": " + inventory[i]);
			}
		}	
	}

	public void addResource(int resource){
		inventory[resource]++;
	}

	public void stashResource(){
		boolean keepGoing = true;
		while (keepGoing){
			System.out.println("Which resource would you like to stash?");
			System.out.println("1.) Wood");
			System.out.println("2.) Stone");
			System.out.println("3.) Food");
			System.out.println("4.) Water");
			System.out.print("Please Enter (1-4)): ");
			int response = getInt(1,4);
			if (inventory[response] != 0){
				inventory[response]--;
				inventory[Items.POINTS] ++;
				System.out.println("You stashed one " + Items.itemStrings[response]);
				System.out.println("You earned one point");
				keepGoing = false;
			}
			else if (inventory[response] == 0) {
				System.out.println("ERROR: No resource to stash");
			}
		}
	}

	public void useBarter(){
		int choices = 0;
		if (inventory[Items.BARTERTOKEN] <= 0){
			System.out.println("No Barter Tokens");
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
				System.out.println("Which resource would you like?");
				System.out.println("1.) Wood");
				System.out.println("2.) Stone");
				System.out.println("3.) Food");
				System.out.println("4.) Water");
				System.out.print("Please Enter (1-4)): ");
				int response = getInt(1,4);
				inventory[response]++;
				System.out.println("You gained one " + Items.itemStrings[response]);
			}
		}
	}

	public void clearResources(){
		for (int i = 1; i <= Items.BARTERTOKEN; i++){
			inventory[i] = 0;
		}
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
