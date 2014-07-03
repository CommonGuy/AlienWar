package alien;

import planet.Move;

public class Hunter extends Alien   {
    private final static byte LIFE=0, STR=1, DEF=2, VIS=3, CLV=4;

    public void setAbilityPoints(float[] abilities) {
        abilities[LIFE] = 0;
        abilities[STR] = 9;
        abilities[DEF] = 0;
        abilities[VIS] = 1;
        abilities[CLV] = 0;
    }

    public Move move(char[][] fields)   {
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

    public boolean wantToFight(int[] enemyAbilities)    {
        return ((double)this.getCurrentHp() * this.getDefenseLvl()) / (double)enemyAbilities[STR] > (enemyAbilities[LIFE] * enemyAbilities[DEF]) / this.getStrengthLvl();
    }
}