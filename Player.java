//Player.java

public class Player {
	int[] inventory = new int[10];
	String name;

	Player(String name){
		this.name = name;
	}

	public static void main(String args[]) {
		Player p = new Player("lucas");
	}	
}
