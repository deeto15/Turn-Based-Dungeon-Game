package lab7;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	static PlayableCharacter pc;
	static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	static String[][] board = new String[7][7];
	static Scanner input;

	public static void main(String[] args) {
		System.out.println("Welcome to the Dungeon!");
		System.out.println("Your goal is to escape the dungeon by getting to the stairs.");
		System.out.println("The stairs are located at (6,6).");

		input = new Scanner(System.in);
		createPlayer();//Prompts the player for their name and gives instructions on how to play.
		createEnemies();//Prompts the player to input information about the enemies

		//
		//GAME LOOP START
		//

		//While the player has hitpoints above 0 and is not in the position of the stairs, continue the game loop.
		while (!(pc.getLocationX() == 6 && pc.getLocationY() == 6) && pc.getCurrentHitPoints() > 0) {
			printStatusOfCharacters();//Print the status of the player and the enemies
			displayBoard(); //Show the game board in the console.
			
			//Player's turn
			pc.move(input);//This polls the player for which direction they want to go and then moves the player in that direction. We pass the Scanner object so the method can use it.
			checkIfPlayerCanAttack();//Loop through enemies ArrayList to see if the player's current position is the same as any enemy. If it is the same as any enemy, attack them.
			
			//Enemies' turn
			doEnemiesTurn();//For each enemy in the ArrayList, call their move and attack methods. Check the Enemy class for more details.
		}
		//
		//GAME LOOP END
		//

		//Print some messages about the status of the player and the enemies
		printFinalStatus();
		input.close();
	}
	
	public void gameLoop() {
		//
		//GAME LOOP START
		//

		//While the player has hitpoints above 0 and is not in the position of the stairs, continue the game loop.
		while (!(pc.getLocationX() == 6 && pc.getLocationY() == 6) && pc.getCurrentHitPoints() > 0) {
			printStatusOfCharacters();//Print the status of the player and the enemies
			displayBoard(); //Show the game board in the console.
			
			//Player's turn
			pc.move(input);//This polls the player for which direction they want to go and then moves the player in that direction. We pass the Scanner object so the method can use it.
			checkIfPlayerCanAttack();//Loop through enemies ArrayList to see if the player's current position is the same as any enemy. If it is the same as any enemy, attack them.
			
			//Enemies' turn
			doEnemiesTurn();//For each enemy in the ArrayList, call their move and attack methods. Check the Enemy class for more details.
		}
		//
		//GAME LOOP END
		//

		//Print some messages about the status of the player and the enemies
		printFinalStatus();

	}

	private static void createPlayer() {
		System.out.println("What is your character's name? (Input name then press enter): ");
		String playerName = input.nextLine();
		//Create instance of PlayableCharacter with inputed name
		pc = new PlayableCharacter(playerName);
		System.out.println("Hello, " + pc.getName());
	}

	//Prompts the player to enter in information about how many enemies they want in the dungeon and what type they should be
	private static void createEnemies() {
		System.out.println("How many enemies are there in this dungeon? (Recommended: 3)");
		int numEnemies = input.nextInt();
		enemies = new ArrayList<Enemy>();
		for (int i = 0; i < numEnemies; i++) {
			System.out.printf("What is Enemy %d's name?\n", i);
			String enemyName = input.next();
			int xLocation = 2; //all enemies start here
			int yLocation = 3; //all enemies start here
			if (i%2==0) { //If i is even
				Enemy temp = new Enemy(enemyName, xLocation, yLocation);
				enemies.add(temp);
			} else { //If i is odd
				Enemy temp = new Zombie(enemyName, xLocation, yLocation);
				enemies.add(temp);
			}
			
		}
	}

	//Prompts the player to enter in information about how many enemies they want in the dungeon and what type they should be
	public static void createEachEnemy(String enemyName) {
		int xLocation = 2; //all enemies start here
		int yLocation = 3; //all enemies start here
		if ((enemies.size()+1)%2==0) { //If i is even
			Enemy temp = new Enemy(enemyName, xLocation, yLocation);
			enemies.add(temp);
		} else { //If i is odd
			Enemy temp = new Zombie(enemyName, xLocation, yLocation);
			enemies.add(temp);
		}
	}

	//Prints the status of the player and the alive enemies
	private static void printStatusOfCharacters() {
		System.out.println(pc);
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).getCurrentHitPoints() > 0) System.out.println(enemies.get(i));
		}
	}

	//Prints several messages about the status of the player and the enemies
	public static void checkIfPlayerCanAttack() {
		for (int i = 0; i < enemies.size(); i++) {
			if (pc.getLocationX() == enemies.get(i).getLocationX() && pc.getLocationY() == enemies.get(i).getLocationY() && enemies.get(i).getCurrentHitPoints() > 0) {
				System.out.printf("%s attacked %s\n", pc.getName(), enemies.get(i).getName());
				pc.attack(enemies.get(i));
				if (enemies.get(i).getCurrentHitPoints() <= 0) {
					System.out.printf("%s defeated %s\n", pc.getName(), enemies.get(i).getName());
				}
			}
		}
	}

	//Loop through enemies and call their doAction method.
	public static void doEnemiesTurn() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).move();
			enemies.get(i).attack(pc);
		}
	}

	//This displays the board by going through 2 for loops and printing characters at each spot depending on if there are characters there.
	public static void displayBoard() {
		for(int y=6;y>=0;y--) {
			for(int x=0;x<=6;x++) {
				if(pc.getLocationX()==x && pc.getLocationY()==y) {
					System.out.print("@");
					board[y][x]="@";
				}else if (x==6 && y==6) {
					System.out.print("$");
					board[y][x]="$";
				}else {
					boolean enemyAtSpot = false;
					for (int i = 0; i < enemies.size(); i++) {
						if (enemies.get(i).getLocationX()==x && enemies.get(i).getLocationY()==y && enemies.get(i).getCurrentHitPoints() > 0) {
							enemyAtSpot = true;
							break;
						}
					}
					if (enemyAtSpot) {
						System.out.print("&");
						board[y][x]="&";
					} else {
						System.out.print(".");
						board[y][x]=".";
					}
				}


			}
			System.out.println();
		}
	}

	//Prints several messages about the status of the player and the enemies
	public static void printFinalStatus() {
		if (pc.getCurrentHitPoints() > 0) {
			System.out.println("You Win!");
		}
		else {
			System.out.println("You Lose!");
		}
		System.out.println("Game Over!");
	}
}