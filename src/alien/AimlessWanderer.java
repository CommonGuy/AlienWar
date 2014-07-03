package alien;

import planet.Move;
import java.util.Random;

public class AimlessWanderer extends Alien{

    @Override
    public void setAbilityPoints(float[] abilities) {
        int cap = 10;
        Random rand = new Random(); 
        for(int i = 0;i<abilities.length; i++){
            abilities[i] = rand.nextInt(cap);
            cap -= abilities [i];
        }
        abilities[rand.nextInt(4)] += cap>0? cap:0;
    }

    @Override
    public Move move(char[][] fields) {
        return Move.getRandom();
    }

    @Override
    public boolean wantToFight(int[] enemyAbilities) {      
        Random rand = new Random();
        rand.setSeed(rand.nextLong());
        return rand.nextBoolean();
    }
}