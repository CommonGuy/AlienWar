package alien;

import planet.Move;
import java.util.Random;

public class AimlessWanderer extends Alien{

    private final Random rand = new Random();

    @Override
    public void setAbilityPoints(float[] abilities) {
        int cap = 10; 
        for(int i = 0;i<abilities.length; i++){
            abilities[i] = rand.nextInt(cap);
            cap -= abilities [i];
        }
        abilities[rand.nextInt(4)] += cap > 0 ? cap : 0;
    }

    @Override
    public Move move(char[][] fields) {
        return Move.getRandom();
    }

    @Override
    public boolean wantToFight(int[] enemyAbilities) {  
        rand.setSeed(rand.nextLong());
        return rand.nextBoolean();
    }
}