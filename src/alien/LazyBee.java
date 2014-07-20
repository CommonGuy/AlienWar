package alien;

import planet.Move;
public class LazyBee extends Alien{

    private static final int LIFE = 0;
    private static final int STRENGTH = 1;

    // Ran trials to figure out what stats were doing  best in sims
    // Lazily assumed that:
        // - Defense is negligeble compared to health
        // - Vision doesn't matter if I'm running east all the time
        // - Cleverness will be canceled out because dumb aliens (yum) won't care
            // and smart aliens probably account for it somehow
    public static float DARWINISM = 4.5f;
    public void setAbilityPoints(float[] abilities){
        abilities[LIFE] = DARWINISM;  
        abilities[STRENGTH] = 10f-DARWINISM;  
    }

    // If bananapeel is fine without trying to move cleverly, I probably am too
    public Move move(char[][] fields)
    {
        return Move.EAST; // This was giving me the best score of all arbitrary moves, for some reason
    }

    // inspired by ChooseYourBattles, tried to do some math, not sure if it worked
        // it seemed that Bee was doing better by being more selective
        // not accounting for cleverness because eh
    public boolean wantToFight(int[] enemyAbilities){
        // chance of hit (h) = (1-(1/((50/deflvl)+1)))) = 50/(deflvl+50)
        double my_h = 50.0/(this.getDefenseLvl() + 50), 
                their_h = (50.0 - enemyAbilities[STRENGTH])/50.0;
        // expected damage (d) = h * (strlvl+1)
        double my_d = /* long and thick */ my_h * (this.getStrengthLvl() + 1),
                their_d = their_h * (enemyAbilities[STRENGTH]); 
        // turns to die (t) = currhp / d
        double my_t = (this.getCurrentHp() / their_d),
                their_t = ((enemyAbilities[LIFE] * 5 + 10) / my_d); // Assume they're at full health because eh
        // worth it (w) = i outlast them by a decent number of turns
            // = my_t - their_t > threshold
            // threshold = 4.5
        boolean w = my_t - their_t > 4.5;

        return w;
    }
}