package alien;

import planet.Move;

public class Coward extends Alien{
    public void setAbilityPoints(float[] abilities){
        abilities[0] = 1;  // life
        abilities[1] = 0;  // str
        abilities[2] = 7; // def
        abilities[3] = 2;  // vis
        abilities[4] = 0;  // clv
    }

    // shamelessly stole Eagle's movement to avoid danger
    public Move move(char[][] fields){
        int vision = getVisionFieldsCount(); 
        char me = fields[vision][vision];
        int leastDanger = Integer.MAX_VALUE;
        Move bestMove = Move.STAY;
        for (Move move : Move.values()) {
            int danger = 0;
            for (int i = 1; i <= vision; i++) {
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

    public boolean wantToFight(int[] enemyAbilities){
        return false;
    }
}