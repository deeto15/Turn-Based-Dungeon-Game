package lab7;

import java.util.Random;

public class Enemy extends Character{
	private Random rand = new Random();
	public Enemy(String name, int locationX, int locationY) {
		super(name, locationX, locationY); //call to our parent class's constructor
		this.setCurrentHitPoints(8); //Change the hitpoints from 0 to 8
		this.setWeaponDamage(2); //Change the weaponDamage from 0 to 2
	}

	//Randomly chooses to move up, down, left, or right
	public void move() { //we use a randomly generated number to automate the moves for the enemies
		if (this.getCurrentHitPoints() > 0) {
			int direction = rand.nextInt(5);
			switch (direction) {
			case 0:
				this.setLocationY(this.getLocationY() + 1); //moves up
				break;
			case 1:
				this.setLocationY(this.getLocationY() - 1);//moves down
				break;
			case 2:
				this.setLocationX(this.getLocationX() - 1);//moves left
				break;
			case 3:
				this.setLocationX(this.getLocationX() + 1);//moves right
				break;
			case 4:
				this.specialAbility();
				break;
			}
		}
	}

	@Override
	public void specialAbility() {
		System.out.println("Enemy Increases weapon damage by 1!");
		this.setWeaponDamage(getWeaponDamage()+1);
	}
}