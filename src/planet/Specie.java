package planet;

import java.awt.Point;

public abstract class Specie {
	//abilities
	private float life = 1;
	private float strength = 1;
	private float defense = 1;
	private float vision = 1;
	private float cleverness = 1;
	//hp
	private int currentHp = 10;
	
	//position
	private int x;
	private int y;
	
	public abstract void setAbilityPoints(float[] abilities);
	public abstract Move move(char[][] fields);
	public abstract boolean wantToFight(int[] enemyAbilities);
	public abstract char getRepresentedChar();
	
	public int getLifeLvl() {
		return Math.round(life);
	}
	
	public int getStrengthLvl() {
		return Math.round(strength);
	}
	
	public int getDefenseLvl() {
		return Math.round(defense);
	}
	
	public int getVisionLvl() {
		return Math.round(vision);
	}
	
	public int getVisionFieldsCount() {
		return Math.round(vision/2);
	}
	
	public int getClevernessLvl() {
		return Math.round(cleverness);
	}
	
	public int getClevernessBlur() {
		return Math.round(cleverness/2) + 1;
	}
	
	public int getCurrentHp() {
		return Math.round(currentHp);
	}
	
	int getAbilitiySum() {
		return Math.round(life + strength + defense + vision + cleverness);
	}
	
	void upgradeAbilities(float[] abilities) {
		life += abilities[0];
		strength += abilities[1];
		defense += abilities[2];
		vision += abilities[3];
		cleverness += abilities[4];
	}
	
	void heal(int hp) {
		currentHp += hp;
	}
	
	void hit(int hitStrength) {
		currentHp -= hitStrength;
	}
	
	Point getPos() {
		return new Point(x, y);
	}
	
	void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	boolean isDead() {
		return currentHp <= 0;
	}
}