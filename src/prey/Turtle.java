package prey;

import planet.Move;

public class Turtle extends Prey {

	public Turtle() {
		representedChar = 'T';
	}
	
	public Move move(char[][] fields) {
		return Move.SOUTHWEST;
	}

	@Override
	public void setAbilityPoints(float[] abilities) {
		abilities[2] = 10; //set all into defense		
	}

}