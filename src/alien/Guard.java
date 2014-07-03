package alien;

import planet.Move;

public class Guard extends Alien{
    public void setAbilityPoints(float[] abilities){
        abilities[0] = 6;  // life
        abilities[1] = 2;  // str
        abilities[2] = 2;  // def
        abilities[3] = 0;  // vis
        abilities[4] = 0;  // clv
    }

    public Move move(char[][] fields){
        return Move.STAY;
    }

    public boolean wantToFight(int[] enemyAbilities){
        return enemyAbilities[1] >= 3;
    }
}