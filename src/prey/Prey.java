package prey;

import planet.Specie;

public abstract class Prey extends Specie {
	protected char representedChar;
	
	public final char getRepresentedChar() {
		return representedChar;
	}
	
	public final boolean wantToFight(int[] enemyAbilities) {
		return false;
	}
}