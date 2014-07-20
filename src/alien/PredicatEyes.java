package alien;

import planet.Move;

/* Predict + Cat = Predicat! */
public class PredicatEyes extends Alien {
    private static final int LIF=0, STR=1, DEF=2, VIS=3;
    private static final int WHALE=6, COW=1, TURTLE=4, EAGLE=3, HUMAN=2, ALIEN=-1, NONE=0;

    @Override
    public void setAbilityPoints( float[] abilities ) {
        abilities[LIF] = 4.5f;
        abilities[STR] = 4.5f;
        abilities[VIS] = 1;
    }

    @Override
    public Move move( char[][] fields ) {
        /* Some credits to Eagle for letting me learn how to do the moves */
        int vision = getVisionFieldsCount(); //count of fields / middle
        int fieldX;
        int fieldY;
        Move bestMove=Move.STAY;
        int bestScore=-1;

       for (Move move : Move.values()) {
            fieldX = vision + move.getXOffset();
            fieldY = vision + move.getYOffset();
            switch(fields[fieldX][fieldY]){
            case 'W' : 
                return move;
            case 'C' :
                if(bestScore<COW){
                    bestMove=move;
                    bestScore=COW;
                }
                break;
            case 'T' :
                if(bestScore<TURTLE){
                    bestMove=move;
                    bestScore=TURTLE;
                }
                break;
            case 'E' :
                if(bestScore<EAGLE){
                    bestMove=move;
                    bestScore=EAGLE;
                }
                break;
            case 'H' :
                if(bestScore<HUMAN){
                    bestMove=move;
                    bestScore=HUMAN;
                }
                break;
            case 'A' :
                if(bestScore<ALIEN){
                    bestMove=move;
                    bestScore=ALIEN;
                }
                break;
            case ' ' :
                if(bestScore<NONE){
                    bestMove=move;
                    bestScore=NONE;
                }
                break;
            }
        }

        if(vision==1 && bestScore>1){
            return bestMove;
        }

        //check immediate outer field
        for (int i=vision-2; i<=vision+2; i++) {
            for(int j=vision-2; j<=vision+2; j++){
                if(i==0 || i==4 || j==0 || j==4){
                    switch(fields[i][j]){
                    case 'W' :
                        bestMove = this.getBestMoveTo(i,j);
                        bestScore = WHALE;
                        break;
                    case 'C' :
                        if(bestScore<COW){
                            bestMove = this.getBestMoveTo(i,j);
                            bestScore = COW;
                        }
                        break;
                    case 'T' :
                        if(i>=vision && j<=vision){
                            return this.getBestMoveTo(i-1,j+1);
                        }
                        if(bestScore<TURTLE){
                            bestMove = this.getBestMoveTo(i,j);
                            bestScore = TURTLE;
                        }
                        break;
                    case 'E' :
                        if(bestScore<EAGLE){
                            bestMove = this.getBestMoveTo(i,j);
                            bestScore = EAGLE;
                        }
                        break;
                    case 'H' :
                        if(i<=vision && j>=vision){
                            return this.getBestMoveTo(i+1,j-1);
                        }
                        if(bestScore<HUMAN){
                            bestMove = this.getBestMoveTo(i,j);
                            bestScore = HUMAN;
                        }
                        break;
                    case 'A' :
                        if(bestScore<ALIEN){
                            bestMove = this.getBestMoveTo(i,j);
                            bestScore = ALIEN;
                        }
                        break;
                    case ' ' :
                        if(bestScore<NONE){
                            bestMove = this.getBestMoveTo(i,j);
                            bestScore = NONE;
                        }
                        break;
                    }
                }
            }
        }

        return bestMove;
    }

    @Override
    public boolean wantToFight( int[] enemyAbilities ) {
        /*
            Fight IF
                1) I CAN BEAT YOU
                2) ????
                3) MEOW!
        */
        float e_hp = enemyAbilities[LIF]*5+10;
        float e_dmg = 1 + enemyAbilities[STR]/2;
        float e_hit = 1 - (1/(50/this.getDefenseLvl()+1));

        float m_hp = this.getCurrentHp();
        float m_dmg = 1 + this.getStrengthLvl()/2;
        float m_hit = 1 - (1/(50/enemyAbilities[DEF]+1));

        return (e_hp/(m_dmg*m_hit) < m_hp/(e_dmg*e_hit));
    }

    private Move getBestMoveTo(int visionX, int visionY){
        int vision = getVisionFieldsCount();

        if(visionX < vision && visionY < vision){
            return Move.NORTHWEST;
        }
        else if(visionX > vision && visionY < vision){
            return Move.NORTHEAST;
        }
        else if(visionX < vision && visionY > vision){
            return Move.SOUTHWEST;
        }
        else if(visionX > vision && visionY < vision){
            return Move.SOUTHEAST;
        }
        else if(visionX == vision && visionY < vision){
            return Move.NORTH;
        }
        else if(visionX == vision && visionY > vision){
            return Move.SOUTH;
        }
        else if(visionX > vision && visionY == vision){
            return Move.EAST;
        }
        else if(visionX < vision && visionY == vision){
            return Move.WEST;
        }
        else{
            return Move.getRandom();
        }

    }
}