package alien;

import planet.Move;

public class BullyAlien extends Alien {

    @Override
    public void setAbilityPoints(float[] abilities) {
        abilities[0] = 2;
        abilities[1] = 8;
        abilities[2] = 0;
        abilities[3] = 0;
        abilities[4] = 0;
    }

    @Override
    public Move move(char[][] fields) {
        return Move.getRandom();
    }

    @Override
    public boolean wantToFight(int[] enemyAbilities) {
        return enemyAbilities[1] < 3;
    }           
}