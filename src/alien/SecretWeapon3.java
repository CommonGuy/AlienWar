package alien;

import planet.Move;

/**
 * Created by VJA1075 on 02/07/14.
 */
public class SecretWeapon3 extends Alien {

    private final static byte LIFE=0, STR=1, DEF=2, VIS=3, CLV=4;

    public void setAbilityPoints(float[] abilities) {
        abilities[LIFE] = 1;
        abilities[STR] = 9;
        abilities[DEF] = 0;
        abilities[VIS] = 0;
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
                    case 'A': danger--;
                    case ' ': danger++;
                    case 'C': danger++;
                    case 'E': danger++;
                    case 'H': danger++;
                    case 'T': danger++;
                    case 'W': danger++;
                    default: danger++;
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
        // LIFE=0, STR=1, DEF=2, VIS=3, CLV=4;
        //abilities[STR]=abilities[STR]*100;
        //((double)this.getCurrentHp() * this.getDefenseLvl()) / (double)enemyAbilities[STR] > (enemyAbilities[LIFE] * enemyAbilities[DEF]) / this.getStrengthLvl()
        return (enemyAbilities[1]+ enemyAbilities[2]  ) < 7;
    }
}