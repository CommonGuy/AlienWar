package alien;

import planet.Move;

public class Stone extends Alien{
    public void setAbilityPoints(float[] abilities){
        abilities[0] = 0;  // Stones are not alive
        abilities[1] = 10;  // Stones are strong
        abilities[2] = 0;  // Stones can't defend themselves
        abilities[3] = 0;  // Stones can't see
        abilities[4] = 0;  // Stones peels can't think
    }

    public Move move(char[][] fields){
        return Move.getRandom(); // Stones don't know where they are going
    }

    public boolean wantToFight(int[] enemyAbilities){
        return enemyAbilities[1] <=2 ; // Stones don't pick fights often.
    }
}