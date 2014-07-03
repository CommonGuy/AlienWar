package alien;

import planet.Move;

public class OkinawaLife extends Alien{
    public void setAbilityPoints(float[] abilities){
        abilities[0] = 10;
    }

    public Move move(char[][] fields){
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
                    case 'C': break;
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

    public boolean wantToFight(int[] enemyAbilities){
        return enemyAbilities[1] == 0;
    }
}