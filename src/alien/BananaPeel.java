package alien;

import planet.Move;
public class BananaPeel extends Alien{
    public void setAbilityPoints(float[] abilities){
        abilities[0] = 0.5f;  // banana peels barely hold themselves together
        abilities[1] = 9.5f;  // banana peels can't help tripping people up
        abilities[2] = 0;  // banana peels can't defend themselves
        abilities[3] = 0;  // banana peels can't see
        abilities[4] = 0;  // banana peels can't think
    }

    public Move move(char[][] fields){
        return Move.STAY; // banana peels can't move
    }

    public boolean wantToFight(int[] enemyAbilities){
        return enemyAbilities[0] == 0 || enemyAbilities[1] == 0;
    }
}