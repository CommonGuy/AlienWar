package alien;

import planet.Move;
import java.util.Random;


public class Morphling extends Alien {

    @Override
    public void setAbilityPoints(float[] abilities) {
        Random rand = new Random();
        for(int attr = 0, maxPoints = 10, value = rand.nextInt(maxPoints); attr <5 && maxPoints > 0 ; attr++, maxPoints -=value, value = rand.nextInt(maxPoints)){
            abilities[attr] = value;
            if(attr == 4 && (maxPoints-=value) > 0){
                abilities[1]+=maxPoints;
            }
        }
    }

    @Override
    public Move move(char[][] fields) {
        int vision = getVisionFieldsCount(); //count of fields / middle
        char me = fields[vision][vision]; //middle of fields
        int leastDanger = Integer.MAX_VALUE;
        Move bestMove = Move.STAY;
        for (Move move : Move.values()) {
            int danger = 0;
            for (int i = 1; i <= vision; i++) { //loop through fields in specific direction
                int fieldX = vision + (i * move.getXOffset());
                int fieldY = vision + (i * move.getYOffset());
                switch(fields[fieldX][fieldY])  {
                    case 'A': danger++;
                    case ' ': break;
                    case 'T': break;
                    case 'E': break;
                    case 'H': break;
                    default: danger--;
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
    public boolean wantToFight(int[] enemyAbilities) {
        return getStrengthLvl() > enemyAbilities[1];
    }

}