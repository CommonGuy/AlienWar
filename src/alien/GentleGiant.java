package alien;

import planet.Move;

public class GentleGiant extends Alien {

    public void setAbilityPoints(float[] abilities) {
        abilities[0] = 4; //life
        abilities[1] = 5; //strength
        abilities[2] = 0; //defense
        abilities[3] = 1; //vision
        abilities[4] = 0; //cleverness
    }

    public Move move(char[][] fields) {
        //you are in the middle of the fields, say fields[getVisionFieldsCount()][getVisionFieldsCount()]
        int vision = getVisionFieldsCount(); 
        Move bestMove=Move.STAY;
         for (Move move : Move.values()) {
            for (int i = 1; i <= vision; i++) {
                int fieldX = vision + (i * move.getXOffset());
                int fieldY = vision + (i * move.getYOffset());
                if (fields[fieldX][fieldY] == 'E') {
                    bestMove=move;
                }
            }
         }
        return bestMove;
    }

    public boolean wantToFight(int[] enemyAbilities) {
        if(enemyAbilities[3]==10 && sumAbilities(enemyAbilities)==10){
            return true;
        }
        else return false;
    }

    int sumAbilities(int[] abilities){
        int sum=0;
        for(int ability : abilities){
            sum+=ability;
        }
        return sum;
    }

}