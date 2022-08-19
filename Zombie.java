package file;

public class Zombie extends Enemy {
	
	public Zombie(String name, int locationX, int locationY) {
		super(name, locationX, locationY);
		this.setCurrentHitPoints(4);
		this.setWeaponDamage(4);
	}
	
	@Override
	public void move() {
	}

	@Override
	public void specialAbility() {
	}

}
