package alien;

import planet.Move;

public class Assassin extends Alien {

    public void setAbilityPoints(float[] abilities) {
        abilities[1] = 10; //strength
        abilities[3] = 0; //vision
    }

    public Move move(char[][] fields) {
        // Get yo' priorities sorted!
        int dir = 0;
        if (getDefenseLvl() < 30) {
            if (dir == 0) {
                dir++;
                return Move.WEST;
            } else if (dir == 1) {
                dir--;
                return Move.SOUTH;
            }
        } else if (getClevernessLvl() < 30) {
            if (dir == 0) {
                dir++;
                return Move.EAST;
            } else if (dir == 1) {
                dir--;
                return Move.NORTH;
            }
        } 
        
        return Move.getRandom();
    }

    public boolean wantToFight(int[] enemyAbilities) {
        return enemyAbilities[2] >= 10 || enemyAbilities[4] >= 10 || enemyAbilities[0] >= 10;
    }

}