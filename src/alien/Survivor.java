package alien;

import planet.Move;

public class Survivor extends Alien {

    private int boldness = 0;
    private float life = 0;
    private float str = 1;
    private float def = 4;
    private float clever = 10 - life - str - def;

    public void setAbilityPoints(float[] abilities) {
        abilities[0] = life; //life
        abilities[1] = str; //strength
        abilities[2] = def; //defense
        abilities[3] = 0; //vision
        abilities[4] = clever; //cleverness
    }

    public Move move(char[][] fields) {
        //you are in the middle of the fields, say fields[getVisionFieldsCount()][getVisionFieldsCount()]
        int vision = getVisionFieldsCount(); //count of fields / middle
    char me = fields[vision][vision]; //middle of fields
    int leastDanger = Integer.MAX_VALUE;
    Move bestMove = Move.STAY;
    for (Move move : Move.values()) {
        int danger = 0;
        for (int i = 1; i <= vision; i++) { //loop through fields in specific direction
            int fieldX = vision + (i * move.getXOffset());
            int fieldY = vision + (i * move.getYOffset());
            switch(fields[fieldX][fieldY]) {
                case 'A':
                    if(boldness < 10)
                        danger++;
                    else
                        danger--;
                    break;
                case ' ':
                    break;
                default:
                    danger-=2;
            }
        }
        if (danger < leastDanger) {
            bestMove = move;
            leastDanger = danger;
        }
    }
    return bestMove;
    }

    public boolean wantToFight(int[] enemyAbilities) {
        //same order of array as in setAbilityPoints, but without cleverness
        boolean fight = boldness < 50;//After 50 fights, believes self unstoppable            
        int huntable = 0;
        for(int ability : enemyAbilities){
            if(ability == 1)
                huntable++;
        }
        if(huntable >= 3){
             fight = true;
        }//if at least 3 of the visible stats are 1 then consider this prey and attack
        else if((float)enemyAbilities[1] / (float)getDefenseLvl() <= (float)getStrengthLvl() + (float)(getClevernessLvl() % 10) / (float)enemyAbilities[2] && enemyAbilities[0] / 5 < getLifeLvl() / 5)
            fight = true;//If I fancy my odds of coming out on top, float division for chance
        if(fight){//Count every scar
            boldness++;//get more bold with every battle
            life += enemyAbilities[0] / 5;
            str += enemyAbilities[1] / 5;
            def += enemyAbilities[2] / 5;
            clever += (10 - (enemyAbilities[0] + enemyAbilities[1] + enemyAbilities[2] + enemyAbilities[3] - 4)) / 5;//count the human cleverness attained or the enemies who buffed clever early
        }
        return fight;
    }

}