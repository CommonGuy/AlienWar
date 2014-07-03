package alien;

import planet.Move;

public class CleverAlien extends Alien {

public void setAbilityPoints(float[] abilities) {
    abilities[0] = 1; //life
    abilities[1] = 0; //strength
    abilities[2] = 0; //defense
    abilities[3] = 0; //vision
    abilities[4] = 9; //cleverness
}

public Move move(char[][] fields) {
    //you are in the middle of the fields, say fields[getVisionFieldsCount()][getVisionFieldsCount()]
    return Move.getRandom();
}

public boolean wantToFight(int[] enemyAbilities) {
    //same order of array as in setAbilityPoints, but without cleverness
    int tmp = (int) ( Math.random() * 2 + 1);
    return tmp == 1;
    }
}