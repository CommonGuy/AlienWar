package alien;

import planet.Move;

public class Junkie extends Alien {
    private int fix = 10;

    public void setAbilityPoints(float[] abilities) {
        abilities[0] = 4; //life
        abilities[1] = 3; //strength
        abilities[2] = 3; //defense
    }

    public Move move(char[][] fields) {
        fix -= 1;
        if (fix == 0) {
            fix = 10;
        }
        else if(fix <= 3 && fix > 0) {
            return Move.getRandom();
        }

        return Move.STAY;
    }

    public boolean wantToFight(int[] enemyAbilities) {
        if(fix > 3) {
            return true;
        }
        // Am I stronger than their defense and can I withstand their attack?
        else if(enemyAbilities[2] < getStrengthLvl() && enemyAbilities[1] < getDefenseLvl()) {
            return true;
        }

        return false;
    }
}