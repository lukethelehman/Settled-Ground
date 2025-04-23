all:
	javac Game.java Player.java Items.java

run: all
	java Game

clean:
	rm *.class
