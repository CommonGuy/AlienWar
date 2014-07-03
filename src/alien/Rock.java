package alien;

import planet.Move;

public class Rock extends Alien{
    public void setAbilityPoints(float[] abilities){
        abilities[0] = 1;
        abilities[1] = 9;
    }

    public Move move(char[][] fields){
        return Move.STAY;
    }

    public boolean wantToFight(int[] enemyAbilities){
        return enemyAbilities[0] + enemyAbilities[1] + enemyAbilities[2] + enemyAbilities[3] < 10;
    }
}