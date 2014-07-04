package alien;

import planet.Move;

public class Randomite extends Alien {

    private static int max = 10;

    @Override
    public void setAbilityPoints(float[] abilities) {
    	int r1;
        while (max > 0) {
            if (max == 1) {
                r1 = 1;
            } else {
                r1 = (int) Math.floor(Math.random() * max);
            }
            int r2 = (int) Math.floor(Math.random() * 5);
            abilities[r2] += r1;
            max -= r1;
        }
    }

    @Override
    public Move move(char[][] fields) {
        return Move.getRandom();
    }

    @Override
    public boolean wantToFight(int[] enemyAbilities) {
        int r3 = (int) Math.floor(Math.random() * 100);
        return r3 > 50;
    }
}