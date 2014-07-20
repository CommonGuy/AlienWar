package alien;

import planet.Move;

public class WeakestLink extends Alien{
    public void setAbilityPoints(float[] abilities){
        abilities[0] = 0;  // life
        abilities[1] = 0;  // str
        abilities[2] = 0;  // def
        abilities[3] = 7;  // vis
        abilities[4] = 3;  // clv
    }

    // the fights will come to you
    public Move move(char[][] fields){
        return Move.STAY; 
    }

    public boolean wantToFight(int[] enemyAbilities){
        return true;
    }
}