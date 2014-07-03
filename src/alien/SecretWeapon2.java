package alien;

import planet.Move;

/**
 * Created by Vaibhav on 02/07/14.
 */
public class SecretWeapon2 extends Alien {

   private final static byte LIFE=0, STR=1, DEF=2, VIS=3, CLV=4;

public void setAbilityPoints(float[] abilities) {
    abilities[LIFE] = 3;
    abilities[STR] = 7;
    abilities[DEF] = 0;
    abilities[VIS] = 0;
    abilities[CLV] = 0;
}

public Move move(char[][] fields)   {
     return Move.getRandom();
}

public boolean wantToFight(int[] enemyAbilities)    {

    return enemyAbilities[1] < 4;
  }
}