package prey;

import planet.Move;

public class Eagle extends Prey {

	public Eagle() {
		representedChar = 'E';
	}
	
	public Move move(char[][] fields) {
		int vision = getVisionFieldsCount(); //count of fields / middle
		char me = fields[vision][vision]; //middle of fields
		int leastDanger = Integer.MAX_VALUE;
		Move bestMove = Move.STAY;
		for (Move move : Move.values()) {
			int danger = 0;
			for (int i = 1; i <= vision; i++) {	//loop through fields in specific direction
				int fieldX = vision + (i * move.getXOffset());
				int fieldY = vision + (i * move.getYOffset());
				if (fields[fieldX][fieldY] != ' ') {
					danger++;
				}
			}
			if (danger < leastDanger) {
				bestMove = move;
				leastDanger = danger;
			}
		}
		return bestMove;
	}

	@Override
	public void setAbilityPoints(float[] abilities) {
		abilities[3] = 10; //set all into vision		
	}
}