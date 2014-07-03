package alien;

import planet.Move;

public class Manager extends Alien {

    private static final int STRENGTH = 5;

    @Override
    public void setAbilityPoints( float[] abilities ) {
        abilities[/* strength   */ 1] = STRENGTH;
        abilities[/* defense    */ 2] = 5;
        abilities[/* cleverness */ 4] = 0; // just to make sure
    }

    @Override
    public Move move( char[][] fields ) {
        return Move.WEST;
    }

    @Override
    public boolean wantToFight( int[] enemyAbilities ) {
        return enemyAbilities[1] < STRENGTH;
    }
}