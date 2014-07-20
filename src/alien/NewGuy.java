package alien;

import planet.Move;

public class NewGuy extends Alien {
    private final static byte LIFE=0, STR=1, DEF=2, VIS=3, CLV=4;

    public void setAbilityPoints(float[] abilities) {
        abilities[LIFE] = 5;
        abilities[STR] = 5;
    }

    public Move move(char[][] fields) {
        // Very rudimentary movement pattern. Tries to engage all "easy" peaceful aliens.
        // Programmer got lazy, so he doesn't run away from danger, decreasing his odds of survival.
        // Afterall, if his species dies, that's one fewer specie that humans have to contend with.

        int vision = getVisionFieldsCount(); //count of fields / middle
        char me = fields[vision][vision]; //middle of fields
        for (Move move : Move.values()) {
            for (int i = 1; i <= vision; i++) { //loop through fields in specific direction
                int fieldX = vision + (i * move.getXOffset());
                int fieldY = vision + (i * move.getYOffset());
                char alienType = fields[fieldX][fieldY];

                if (alienType == 'E' || alienType == 'H' || alienType == 'T' || alienType == 'W') {
                    return move;
                }
            }
        }

        return Move.getRandom();
    }

    public boolean wantToFight(int[] enemyAbilities) {
        if (isWhale(enemyAbilities)) {
            return true;
        } else if (isCow(enemyAbilities)) {
            return false; // Cows hit hard!
        } else if (isTurtle(enemyAbilities)) {
            return true;
        } else if (isEagle(enemyAbilities)) {
            return true;
        } else if (isHuman(enemyAbilities)) {
            if (enemyAbilities[STR] < 3) {
                return true;
            }
        }

        return false;
    }

    public boolean isWhale(int[] enemyAbilities) {
        return enemyAbilities[LIFE] == 10 && totalAbilityPoints(enemyAbilities) == 10;
    }

    public boolean isCow(int[] enemyAbilities) {
        return enemyAbilities[STR] == 10 && totalAbilityPoints(enemyAbilities) == 10;
    }

    public boolean isTurtle(int[] enemyAbilities) {
        return enemyAbilities[DEF] == 10 && totalAbilityPoints(enemyAbilities) == 10;
    }

    public boolean isEagle(int[] enemyAbilities) {
        return enemyAbilities[VIS] == 10 && totalAbilityPoints(enemyAbilities) == 10;
    }

    public boolean isHuman(int[] enemyAbilities) {
        return !(isWhale(enemyAbilities) || isCow(enemyAbilities) || isTurtle(enemyAbilities)) && totalAbilityPoints(enemyAbilities) >= 10;
    }

    public int totalAbilityPoints(int[] enemyAbilities) {
        return enemyAbilities[LIFE] + enemyAbilities[STR] + enemyAbilities[DEF] + enemyAbilities[VIS];
    }
}