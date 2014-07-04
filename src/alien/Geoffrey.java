package alien;

import planet.Move;

public class Geoffrey extends Alien{
    public void setAbilityPoints(float[] abilities){
        abilities[0] = 5;  
        abilities[1] = 5;  
        abilities[2] = 0; 
        abilities[3] = 0;  
        abilities[4] = 0;  
    }

    public Move move(char[][] fields){      
        return Move.getRandom();
    }

    public boolean wantToFight(int[] enemyAbilities){
        return (this.getStrengthLvl() * this.getCurrentHp() * 0.15 > enemyAbilities[0] * enemyAbilities[1]);
    }
}