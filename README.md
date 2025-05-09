# Settled Ground  
**Lucas Lehman CS121 Final Project Documentation**

---

## Summary

Settled Ground is a simple text-based resource-collecting dice game. It will be played through a command line with 2 players. The players’ goal is to get to 25 survival points by collecting resources, building structures, and avoiding the pesky bandit.

The purpose of this project is to demonstrate concepts learned in CS-121 and to learn to manage a project on my own.

My biggest goal is to get the basic mechanics working. After that I will need to tweak the point system and bandit probability to make the game more balanced and fun. After that I'll add the ability to save and load games. If I accomplish all of that I would also love to try and program computer players and run simulations to see which strategies work best.

---

## Game Rules

**Players:** 2  
**Goal:** Be the first player to reach 25 survival points

**On each turn**
- Roll the die and collect your resource
- 1 action is allowed per turn:
  - **Continue** – nothing happens
  - **Barter** – use barter token to add one of any other resource to your inventory
  - **Stash** – stash 1 resource to earn 1 point (any item except barter tokens can be stashed. Stashing a resource removes it from inventory)
  - **Build** - use resources to build a structure for points

**Die Values:**
- 1: Bandit (if rolled, 25% chance to lose all resources)
- 2: Wood
- 3: Stone
- 4: Food
- 5: Water
- 6: Barter token
---

### Structure build costs/points:

| Structure      | Wood | Stone | Food | Water | Barter Token | Points |
|----------------|------|-------|------|--------|---------------|--------|
| Fire Pit       | 1    | 1     |      |        |               | 3      |
| Shelter        | 1    |       | 1    | 1      |               | 5      |
| Cabin          | 2    | 2     | 1    | 1      |               | 18     |
| Outpost        | 1    | 1     | 1    | 1      | 1             | 10     |
*(Barter tokens now worth 2 resources when used for bartering)*

---

## Sample Run

```plaintext
Welcome to Settled ground!
…blah blah blah

Game Menu
1.) Start Game  
2.) Rules/Instructions  
3.) Exit Program  
Please Enter (1-3): 1

Player Selection 
Name of Player 1: Lucas  
Name of Player 2: Levi

A coin was flipped, and Lucas will roll first

. . . Lucas rolls a 3  
Lucas collects 1 Water  
Lucas’s Inventory  
- 0 Points  
- 1 Water

Actions  
1.) Continue  
2.) Stash  
3.) Barter  
4.) Build  
5.) Rules/Instructions  
6.) Exit Game  
Please Enter (1-6): 2

What item would you like to stash?  
1.) Water  
Please Enter the item number: 1

You stashed 1 Water and earned one Point!

. . . Levi rolls a 1 and encounters a Bandit...  
He gets lucky and escapes with his inventory

Levi’s Inventory  
- 0 points

Actions  
1.) Continue  
2.) Stash  
3.) Barter  
4.) Build  
5.) Rules/Instructions  
6.) Exit Game  
Please Enter (1-6): 1

. . . Lucas rolls a 6
Lucas collects 1 Barter Token
Lucas’s Inventory
- 1 Points
- 1 Barter Token

Actions
1.) Continue 
2.) Stash
3.) Barter
4.) Build
5.) Rules/Instructions
6.) Exit Game
Please Enter (1-6): 3

Barter for one resource of your choice
1.) Food
2.) Water
3.) Wood
4.) Stone 
Please Enter (1-4): 4

Lucas collects 1 Stone

. . . Levi rolls a 5 
Levi collects 1 Stone
Levi’s Inventory (Now jumping to late game)
- 5 points
- 1 Food
- 1 Water 
- 1 Wood 
- 2 Stone
- 1 Barter Token

Actions
1.) Continue 
2.) Stash
3.) Barter
4.) Build
5.) Rules/Instructions
6.) Exit Game
Please Enter (1-6): 4

Structures
1.) Fire Pit (3pts) (1 Wood, 1 Stone) 
2.) Shelter (5pts) (1 Wood, 1 Food, 1 Water)
3.) Cabin (10pts) (2 Wood, 2 Stone, 1 Food, 1 Water)
4.) Outpost (10pts) (1 of each item)
Please Enter (1-4): 4

Levi Builds an Outpost for ten points. Barter Tokens can now be traded in for 2 resources  
```
```mermaid
classDiagram

    Game <-- Player
    Game <-- Items
    Player <-- Items
    class Game {
        #Player p1
        #Player p2
        +void main()
        +void start()
        +string gameMenu()
        +void selectPlayers()
        +void takeTurn(Player player)
        +string actionMenu()
        +void printRules()
        +int rollDie()
        +int flipCoin()
        
    }
    
    class Player{
      #string name
      #int[] inventory 
      +Player()
      +setName(String name)
      +void printInventory()
      +void addResource(int resource)
      +void stashResource()
      +void usebarter()
      +void clearInventory()
      +boolean canBuild()
      +void build()      
    }
    class Items {
        +constant WOOD = 0
        +constant STONE = 1
        +constant FOOD = 2
        +constant WATER = 3
        +constant BARTERTOKEN = 4
        +constant FIREPIT = 5
        +constant SHELTER = 6
        +constant CABIN = 7
        +constant OUTPOST = 8
        +constant POINTS = 9
        +String[] itemStrings
    }
```
---

## Classes

### Player

- Holds the inventory array, the main data structure of this program  
- Each index in the inventory array represents a resource, with the last few indices for structures like Outpost or Shelter and Points  
- Resource types will be defined as constant integers in the Items class to make resource manipulation clear (`player1.addResource(Items.WOOD)`)  
- Each player has a name property, set by the user at the beginning of the program  

**Methods**  
- `printInventory()` — prints out the players inventory by iterating through inventory[], only prints if the value is not zero (except points)
- `addResource(resource)` — adds 1 to the specific resource in inventory[], called right after each dice roll  
- `stashResource()` — prompts for which resource to stash – checks to make sure it’s not zero, subtracts one from that resource and adds one to points, called from the action menu  
- `useBarter()` — checks for barter token, if not zero than subtracts 1 from the barter tokens and prompts for which resource to add. Also checks for an Outpost. If there is one or more outpost a prompt for two resources will be given and added to inventory  
- `clearResources()` — changes all resources to zero except points, called if the bandit is encountered  
- `build()` — stores an array of int equal in length to inventory for each structure’s specific build cost, prompts user for which structure they’d like to build, calls canBuild to check if possible, subtracts the build cost from the inventory, adds the amount of points earned and adds 1 to the structure built  
- `canBuild(structureCost[])` — Boolean method checking to see if a structure can be built, loops through arrays making sure each value in inventory is greater than or equal to the structure cost, returns true or false
- `getInt()` - get users string input and return a int

---

### Game

- Contains two instances of Player  

**Methods**  
- `main()` — Instantiates game, calls `start()`  
- `start()` — displays `gameMenu()` and exits, prints rules, or starts game. Once the game starts, call `namePlayers()`, flip a coin (`flipCoin()`) to see who goes first, then it loops alternating `takeTurn()` until one player has 25 or more points  
- `gameMenu()` — shows options and returns a string with player response  
- `selectPlayers()` — asks users for names and stores them into their name attribute  
- `takeTurn()` — rolls a die (`rollDie()`), if bandit, flip coin (`flipCoin()`) and see if inventory gets removed. For everything else it adds the resource to player’s inventory, prints out their inventory, displays `actionMenu()` and follows the user’s choice calling methods like add/stash resource, build, etc.  
- `actionMenu()` — shows options and returns string with player input  
- `printRules()` — prints detailed rules of the game with structure costs and items all explained  
- `rollDie()` — returns random int 1–6  
- `flipCoin()` — returns random int 0–1  
  - actually probability might not be 50–50 depending on how annoying the bandit ends up being

---

### Items

Declares an integer value for each resource/points/structures so that both game and player can import them and make working with resources clearer than trying to remember array indices

```
constant WOOD = 0
constant STONE = 1
constant FOOD = 2
constant WATER = 3
constant BARTERTOKEN = 4
constant FIREPIT = 5
constant SHELTER = 6
constant CABIN = 7
constant OUTPOST = 8
constant POINTS = 9
{"Points", "Wood", "Stone", "Food", "Water", 
"Barter Tokens", "Fire Pits", "Shelters", 
"Cabins", "Outposts"}

```

---

## Milestones

- get UML approved  
- Write Items 
- Build basic Player class  
- Write every Player method and test individually  
- Write basic Game class  
- Write the Menus  
- Write rollCoin and rollDie  
- Write selectPlayers  
- get one takeTurn working  
- write top level start() control  
- test game thoroughly  

---

## Stretch Goals

- tweak structure costs, coin probability, and win condition to balance game and try to make it fun  
- add the ability to save to a file (serialization, or simply printing inventory and name?)

## Very Stretchy Goals
- write a basic computer player with limited strategy
- write another computer with a legit strategy
- test out multiple different strategies and rules and run a bunch of simulations to see which one wins the most


## Pseudocode

**Player**
```
void printInventory()
  print name + "'s Inventory"
  print number of points
  for each item in length Item.itemStrings
    if inventory[item] is not equal to zero
    print itemStrings[item] + ": inventory[item]"

void addResource(resource)
  add one to inventory[resource]

boolean stashResource()
  keepGoing gets true
  while keepGoing 
    print "What resource would you like to stash. "1.)Wood 2.)Stone ... 5.) Exit Please enter (1-5)"
    store getInt() in response
      if response equals 5
        return false
      if response is >= 1 and <= 4
        if inventory[response] is not equal to 0
          add one to inventory[POINTS]
          subtract one from inventory[response]
          print "you stashed one" itemStrings[response] to gain one point"
          return true
      else if inventory[response] = 0
        print "No item to stash"
      else
        print "please enter an int"

void useBarter() 
  int choices gets 0
  if inventory[Items.BARTER] is less than or equal to zero
    print "no barter tokens
  else
     subtract 1 from barter tokens
     if inventory[Items.OUTPOST] is not equal to 0
       choices = 2
     else
       choices = 1
    for int i = 0; i <= choices; i++
      prompt for resource
      store getInt(1,4) in response
      add one to inventory[response]
      print "you gained one " inventory[response]

void clearResources()
  for i = 1; i < Items.BARTERTOKEN; i++
    inventory[i] gets 0

boolean build()
  boolean keepGoing equals true;
  while keepGoing 
    prompt user for which structure they’d like to build
    response gets getInt(1,4)
    int[] cost = new int[10]
    if response = 1, cost = costFirepit
    if response = 2, cost = costShelter
    if response = 3, cost = costCabin
    if response = 4, cost = costOutpost
    if response = 5, return false
  
    if canBuild(cost[]) is true 
      for (int i = 1; i <= Items.BARTERTOKEN; i++) {
        subract cost[i] from inventory[i] 
      add amount of points of structure to inventory
      add 1 to the values of the structure built
      print "you built one " Items.itemStrings[response + 5]
      return true 
    else
      print "not enough resources to build"
    
boolean canBuild(int[] cost)
    for (int i = 1; i <= barter tokens; i++)
      if inventory[i] < cost[i]
        return false
    return true

int getInt(int lowBound, int upBound)
  keepGoing gets true
  int result gets 0
  while keepGoing is true
    get user input
      try to parse it to an int
        if input is within upper and lower bounds
          keepgoing gets false
        else print error message
      catch error message if failed
  return result
```

**Game**
```
main()
  calls start()

Game()
  initialize p1 and p2

start()
  boolean keepGoing gets true
  while keepGoing 
    String response gets gameMenu()
    if response equals 0
      exit
    if response equals 1
      printRules()
    if response equals 2
      call selectPlayers() to set user names
      int coin gets flipCoin()
      if coin equals 0
         Player first gets p1
         Player second gets p2
      if coin equals 1
        Player first gets p2
        Player second gets p1
      boolean noWinner gets true
      while noWinner
        takeTurn(first)
        if first's points are greater than or equal to 25
          print "first.name wins!"
          noWinner gets false
        takeTurn(second)
        if second's points are greater than or equal to 25
        print "second.name wins!"
        noWinner gets false

String gameMenu()
  print menu ("0.)Exit 1.)View rules 2.)Start"
  response equals getInt(0,2)
  return response

printRules()
  print the full rules out

selectPlayers()
  print "Player 1 name: "
  player1Name gets input.nextLine
  p1 gets new Player(player1Name)
  print "Player 2 name: "
  player2Name gets input.nextLine
  p2 gets new Player(player2Name)

flipCoin(double chanceOfTrue)
  double toss gets random double from 0 to 1
  if toss is less than chanceOfTrue
    return true
  else
    return false
rollDie()
  return random int 1-6

int actionMenu()
  Print turn options ("0.) Continue  1.) Stash  2.) Barter  3.) Build  4.) Rules/Instructions  5.) Exit Game")
  Print ("Please Enter (0-5): ")
  int response gets getInt(0,5)
  return response

void takeTurn()
  int roll gets rollDie()
  print "... player.name rolls a <roll>"

  if roll equals 1
    print ("and encounters the bandit!!")
      print ("player.name flips a coin. if tails, they lose all their resources")
      string coin gets flipCoin(.5)
      if coin equals "tails"
        print ("player.name cannot escape the bandit, and loses all of their resources");
        player.clearInventory()
      else
        print ("player.name narrowly escapes the bandit with all of their resources");
  else if roll equals 2
    player.addResource(Items.WOOD)
    print ("and collects one Wood")
  else if roll equals 3
    player.addResource(Items.STONE)
    print ("and collects one Stone")
  else if roll equals 4
    player.addResource(Items.FOOD)
    print ("and collects one Food")
  else if roll equals 5
    player.addResource(Items.WATER)
    print ("and collects one Water")
  else if roll equals 6
    player.addResource(Items.BARTERTOKEN)
    print ("and collects one Barter Token")

  player.printInventory()

  boolean keepGoing gets true
  while keepGoing
    int response gets actionMenu()
    if response equals 0
      keepGoing gets false
    else if response equals 1
      if player.stashResource() is true
        keepGoing gets false
    else if response equals 2
      if player.useBarter() equals true
        keepGoing gets false
    else if response equals 3
      if player.build equals true
        keepGoing gets false
    else if response equals 4
      printRules()
    else if response equals 5
      start()
      
      
      
    
  
  
    




       

  
