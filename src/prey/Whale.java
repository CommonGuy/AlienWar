package prey;

import planet.Move;

public class Whale extends Prey {

	public Whale() {
		representedChar = 'W';
	}
	
	public Move move(char[][] fields) {
		return Move.STAY;
	}

	@Override
	public void setAbilityPoints(float[] abilities) {
		abilities[0] = 10; //set all into life		
	}

}