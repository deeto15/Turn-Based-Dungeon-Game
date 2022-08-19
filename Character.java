package lab7;

public abstract class Character {
	//class fields
	private String name;
	private int currentHitPoints = 0;
	private int weaponDamage = 0;
	private int locationX;
	private int locationY;
	
	/**
	 * @param name
	 * @param locationX
	 * @param locationY
	 */
	public Character(String name, int locationX, int locationY) {
		this.name = name;
		this.locationX = locationX;
		this.locationY = locationY;
	}
	
	//Lower our current hitpoints by the damageTaken argument
	public void receiveDamage(int damageTaken) {
		this.currentHitPoints -= damageTaken;
	}
	//Object interaction. The parameter is an Character object
	//Check if this character is in the same location as the other character, if so then do weapon damage to them
	//We call the opponent's receiveDamage method within this attack method
	public void attack(Character other) {
		if (other.getLocationX() == this.getLocationX() && other.getLocationY() == this.getLocationY() && other.getCurrentHitPoints() > 0){
			System.out.printf("%s attacked %s\n", this.getName(), other.getName());
			other.receiveDamage(this.weaponDamage);
			if (other.getCurrentHitPoints() <= 0) {//if true, then the other character is defeated
				System.out.printf("%s defeated %s\n", this.getName(), other.getName());
			}
		}
		
	}
	
	//Abstract method that needs to be implemented in child classes.
	//The actual implementation depends on the subclass
	public abstract void specialAbility();
	
	public int getCurrentHitPoints() {
		return currentHitPoints;
	}

	public int getWeaponDamage() {
		return weaponDamage;
	}

	public String getName() {
		return name;
	}

	public int getLocationX() {
		return locationX;
	}

	public void setLocationX(int locationX) {
		if (locationX >= 0 && locationX <= 6)
			this.locationX = locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	public void setLocationY(int locationY) {
		if (locationY >= 0 && locationY <= 6)
			this.locationY = locationY;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCurrentHitPoints(int currentHitPoints) {
		this.currentHitPoints = currentHitPoints;
	}

	public void setWeaponDamage(int weaponDamage) {
		this.weaponDamage = weaponDamage;
	}
	
	public String toString() {
		return String.format("%s location: %d, %d. HitPoints: %d", this.getName(), this.getLocationX(), this.getLocationY(), this.getCurrentHitPoints());
	}
	
	public boolean equals(Object other) {
		//Cast the Object parameter to a Character so we can access its name
		Character tempCast = (Character)other;
		if (this.name.equals(tempCast.getName())){
			return true;
		}
		else {
			return false;
		}
	}
}