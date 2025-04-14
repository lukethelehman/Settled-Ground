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
			System.out.println("0.) Wood");
			System.out.println("1.) Stone");
			System.out.println("2.) Food");
			System.out.println("3.) Water");
			System.out.print("Please Enter (0-3)): ");
			int response = getInt();

			if (response >= 0 && response <= 3){
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
				System.out.println("ERROR: Please enter an integer (0-3)");
			}
		}	
	}

	public int getInt(){
		String response = input.nextLine();
		boolean keepGoing = true;
		while (keepGoing){
			try {	
				keepGoing = false;
				return Integer.parseInt(response);
			} catch(NumberFormatException e) {
				System.out.println("ERROR: please enter an integer");
			}
		}
	}
}
