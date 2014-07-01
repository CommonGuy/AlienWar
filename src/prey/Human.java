package prey;

import planet.Move;

public class Human extends Prey {

	public Human() {
		representedChar = 'H';
	}
	
	public Move move(char[][] fields) {
		return Move.NORTHEAST;
	}

	@Override
	public void setAbilityPoints(float[] abilities) {
		abilities[4] = 10; //set all into cleverness		
	}
}