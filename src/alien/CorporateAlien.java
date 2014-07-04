package alien;

import planet.Move;

public class CorporateAlien extends Alien {

    float[] abilities = new float[5];

    public void setAbilityPoints(float[] abilities) {
        abilities[0] = 4; // life
        abilities[1] = 6; // strength
        abilities[2] = 0; // defense
        abilities[3] = 0; // vision
        abilities[4] = 0; // cleverness
    }

    public Move move(char[][] fields) {
        int vision = getVisionFieldsCount();
        char me = fields[vision][vision]; // middle of fields
        int potentialPrey = Integer.MAX_VALUE;
        Move bestMove = Move.getRandom();
        for (Move move : Move.values()) {
            int goodPrey = 0;
            for (int i = 1; i <= vision; i++) { // loop through fields in specific direction
                int fieldX = vision + (i * move.getXOffset());
                int fieldY = vision + (i * move.getYOffset());


                // Try to ignore other aliens
                if (fields[fieldX][fieldY] == 'A') {
                    goodPrey--;
                } else if (fields[fieldX][fieldY] != ' ') { // Look for interesting prey that gives good stat boosts: whale, cow or turtle
                    if(getLifeLvl() > 10){
                        goodPrey++;
                        if (getLifeLvl() < (10 + abilities[0] * 5) * 0.4 && fields[fieldX][fieldY] != 'W') //start prioritizing whales once at 40% health in order to heal
                            goodPrey++;
                    } else goodPrey--; // Always run away when near death
                }

            }
            if (goodPrey > potentialPrey) {
                bestMove = move;
                potentialPrey = goodPrey;
            }
        }
        return bestMove;

    }

    public boolean wantToFight(int[] enemyAbilities) {
        // if it's a whale, always engage when above 10hp
        int combatLvl = enemyAbilities[0] + enemyAbilities[1] + enemyAbilities[2];

        if (getLifeLvl() > 10 && enemyAbilities[0] == 10 && enemyAbilities[1] != 10 && enemyAbilities[2] != 10 && combatLvl == 10){
            return true;
        } else 
            // if it's a cow, always engage when above 15hp
            if(getLifeLvl() > 15 && enemyAbilities[0] != 10 && enemyAbilities[1] == 10 && enemyAbilities[2] != 10 && combatLvl == 10) {
            return true;
        } else 
            // if it's a turtle, always engage when above 10hp
            if(getLifeLvl() > 10 && enemyAbilities[0] != 10 && enemyAbilities[1] != 10 && enemyAbilities[2] == 10 && combatLvl == 10) {
            return true;
        } else 
            //Puny Eagle is weak; CorporateAlien crush puny eagle
            if (combatLvl == 0 && getLifeLvl() > 10) {
                return true;
            } else {
            float dodgeChance = (float) (abilities[2] +1 <= 33 ? 1.333 : 1.5);
            if(getLifeLvl() / ((enemyAbilities[1]/2)+1) > (((enemyAbilities[0]*5)+10) * dodgeChance) / abilities[1]/2 )
                return true;
        }           
        return false;
    }
}