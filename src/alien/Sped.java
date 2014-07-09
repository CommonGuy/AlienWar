package alien;

import planet.Move;

public class Sped extends Alien {

    private final int LIF = 0, STR = 1, DEF = 2, VIS = 3, CLV = 4;

    public void setAbilityPoints(float[] abilities) {
        abilities[LIF]  = 8;
        abilities[DEF]  = 2;
    }

    public Move move(char[][] fields) {
        // He's a little clumsy so he doesn't move because he knows he'll fall and trip.
        return Move.STAY;
    }

    public boolean wantToFight(int[] enemyAbilities) {
        // He's a, well, me... a sped. He doesn't know if he wants to fight or not.
        int r = (int) Math.floor(Math.random() * 100);
        return r > 50;
    }

}