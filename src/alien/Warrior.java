package alien;

import planet.Move;

public class Warrior extends Alien {

    public void setAbilityPoints(float[] abilities) {
        abilities[0] = 5;
        abilities[1] = 5;
    }

    public Move move(char[][] fields) {
        return Move.getRandom(); //enemies are everywhere!
    }

    public boolean wantToFight(int[] enemyAbilities) {
        return true; //strong, therefore no stronger enemies.
    }
}