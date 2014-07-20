package alien;

import planet.Move;

public class Eli extends Alien {

    public void setAbilityPoints(float[] abilities) {
        abilities[0] = 3; //life
        abilities[1] = 3; //strength
        abilities[2] = 4; //defense
        abilities[3] = 0; //vision
        abilities[4] = 0; //cleverness
    }

    public Move move(char[][] fields) {
        //Just keep going west as that is the way to salvation
        return Move.WEST;
    }

    public boolean wantToFight(int[] enemyAbilities) {
        //Will not fight as it's not the moral thing to do
        return false;
    }

}