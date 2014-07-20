package alien;

import planet.Move;

public class HerjanAlien extends Alien{

    private final int LIFE = 0, STR = 1;

    private final int UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3, DOWNLEFT = 4, DOWNRIGHT = 5, UPLEFT = 6, UPRIGHT = 7;

    @Override
    public void setAbilityPoints(float[] abilities) {
        abilities[LIFE] = 4.5f;
        abilities[STR] = 5.5f;
    }

    @Override
    public Move move(char[][] fields) {     

        int middle = getVisionFieldsCount();

        int neededAttacksToWin = 60/((getStrengthLvl()-1)/2+1);
        if(getCurrentHp()-2 > neededAttacksToWin){
            if(fields[middle-1][middle-1] == 'W')
                return Move.NORTHWEST;
            if(fields[middle][middle-1] == 'W')
                return Move.NORTH;
            if(fields[middle+1][middle-1] == 'W')
                return Move.NORTHEAST;
            if(fields[middle+1][middle] == 'W')
                return Move.EAST;
            if(fields[middle+1][middle+1] == 'W')
                return Move.SOUTHEAST;
            if(fields[middle][middle+1] == 'W')
                return Move.SOUTH;
            if(fields[middle-1][middle+1] == 'W')
                return Move.SOUTHWEST;
            if(fields[middle-1][middle] == 'W')
                return Move.EAST;
        }

        if(getCurrentHp() > 5){
            if(fields[middle+1][middle-1] == 'T')
                return Move.STAY;
            if(fields[middle][middle-1] == 'T')
                return Move.WEST;
            if(fields[middle+1][middle] == 'T')
                return Move.SOUTH;

            if(fields[middle-1][middle-1] == 'H')
                return Move.STAY;
            if(fields[middle][middle-1] == 'H')
                return Move.EAST;
            if(fields[middle-1][middle] == 'H')
                return Move.NORTH;
        }

        int[] danger = new int[8];
        for(int i = 0; i < 8; i++){
            danger[i] = 0;
        }

        for(int x = -getVisionFieldsCount(); x <= getVisionFieldsCount(); x++){
            for(int y = -getVisionFieldsCount(); y <= getVisionFieldsCount(); y++){

                if(fields[middle - x][middle - y] == 'A'){
                    int dangerFactor = getVisionFieldsCount();
                    if(x <= 1 && x >= -1 && y <= 1 && y >= -1)
                        dangerFactor = getVisionFieldsCount()*4;

                    if(x<0)
                        danger[LEFT] += dangerFactor;
                    if(x>0)
                        danger[RIGHT] += dangerFactor;
                    if(y<0)
                        danger[UP] += dangerFactor;
                    if(y>0)
                        danger[DOWN] += dangerFactor;

                    if(x<=0 && y<=0)
                        danger[UPLEFT] += dangerFactor;
                    if(x>=0 && y<=0)
                        danger[UPRIGHT] += dangerFactor;
                    if(x<=0 && y>=0)
                        danger[DOWNLEFT] += dangerFactor;
                    if(x>=0 && y>=0)
                        danger[DOWNRIGHT] += dangerFactor;
                }
            }
        }
        boolean safe = true;
        for(int i = 0; i < 4; i++){
            if(danger[i] > 3){
                safe = false;
                break;
            }
        }
        if(safe)
            return Move.STAY;

        int leastDanger = 50;
        int bestWay = 0;
        if(getVisionFieldsCount()>1){
            for(int j = 8; j >= 0; j--){
                if(danger[j] < leastDanger){
                    leastDanger = danger[j];
                    bestWay = j;
                }
            }
        }else{
            for(int j = 4; j >= 0; j--){
                if(danger[j] < leastDanger){
                    leastDanger = danger[j];
                    bestWay = j;
                }
            }
        }

        if(bestWay == LEFT)
            return Move.WEST;
        if(bestWay == UP)
            return Move.NORTH;
        if(bestWay == RIGHT)
            return Move.EAST;
        if(bestWay == DOWN)
            return Move.SOUTH;
        if(bestWay == DOWNLEFT)
            return Move.SOUTHWEST;
        if(bestWay == DOWNRIGHT)
            return Move.SOUTHEAST;
        if(bestWay == UPLEFT)
            return Move.NORTHWEST;
        if(bestWay == UPRIGHT)
            return Move.NORTHEAST;

        return Move.getRandom();
    }

    @Override
    public boolean wantToFight(int[] enemyAbilities) {

        int enemyLifeLvl = enemyAbilities[LIFE];
        int enemyStrengthLvl = enemyAbilities[STR];

        int neededAttacksToWin = (enemyLifeLvl*5+10)/((getStrengthLvl()-1)/2+1);
        int enemyAttacksToLose = (getCurrentHp()/5/((enemyStrengthLvl-1)/2+1));

        if(getCurrentHp() <= enemyStrengthLvl)
            return false;
        if(enemyAttacksToLose-neededAttacksToWin > enemyStrengthLvl/enemyLifeLvl+1)
            return true;
        return false;
    }

    @Override
    public String toString(){ // for some testing
        return "Herjan";
    }
}