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
		p.printInventory();
		p.stashResource();
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
			System.out.println("What item would you like to stash?");
			System.out.println("1.) Wood");
			System.out.println("2.) Stone");
			System.out.println("3.) Food");
			System.out.println("4.) Water");
			System.out.print("Please Enter (1-4)): ");
			int response = getInt();

			if (response >= 1 && response <= 4){
				if (inventory[response] != 0){
					inventory[response] = inventory[response] - 1;
					inventory[Items.POINTS] ++;
					System.out.println("You stashed one " + Items.itemStrings[response]);
					System.out.println("You earned one point");
					keepGoing = false;
				}
				else if (inventory[response] == 0) {
					System.out.println("ERROR: No item to stash");
					}
			}
			else {
				System.out.println("ERROR: Please enter an integer (1-4)");
			}
		}	
	}

	public int getInt(){
		boolean keepGoing = true;
		String response;
		int result = 0;
		while (keepGoing){
			response = input.nextLine();
			try {
				result = Integer.parseInt(response);
				keepGoing = false;
			} catch(NumberFormatException e) {
				System.out.print("ERROR: please enter an integer: ");
			}
		}
		return result;
	}
}
