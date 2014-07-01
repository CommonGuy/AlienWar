package prey;

import planet.Move;

public class Cow extends Prey {

	public Cow() {
		representedChar = 'C';
	}
	
	public Move move(char[][] fields) {
		return Move.getRandom();
	}

	@Override
	public void setAbilityPoints(float[] abilities) {
		abilities[1] = 10; //set all into strength
		
	}

}