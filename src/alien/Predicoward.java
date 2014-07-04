package alien;

import planet.Move;
import java.util.ArrayList;
import java.awt.Point;

/* Predict + Cat = Predicat! */
public class Predicoward extends Alien {
    /*
        - - - - - - - - - - - - -
        - - - - - - - - - - - - -
        - - - - - - - - - - - - -
        - - - - - - - - - - - - -
        - - - - - - - - - - - - -
        - - - - - - - - - - - - -
        - - - - - - P - - - - - -
        - - - - - - - - - - - - -
        - - - - - - - - - - - - -
        - - - - - - - - - - - - -
        - - - - - - - - - - - - -
        - - - - - - - - - - - - -
        - - - - - - - - - - - - -

        Risk score = sum of manhattan distances of all aliens within vision range
    */
    @Override
    public void setAbilityPoints( float[] abilities ) {
        abilities[3] = 10;
    }

    @Override
    public Move move( char[][] fields ) {
        /* Some credits to Eagle for letting me learn how to do the moves */
        int vision = getVisionFieldsCount(); //count of fields / middle

        Move bestMove=Move.STAY;
        int bestRiskScore=10000;
        int riskScore=0;
        ArrayList<Point> aliens = new ArrayList<Point>();

        //generate alien list
        for (int x=0; x<=vision*2; x++) {
            for(int y=0; y<=vision*2; y++){
                if(fields[x][y]=='A'){
                    aliens.add(new Point(x,y));
                }
            }
        }

        for (Move move : Move.values()) {
            int x = vision + move.getXOffset();
            int y = vision + move.getYOffset();
            riskScore = 0;

            for(Point alienCoord : aliens){
                riskScore += this.getDistance(x, y, alienCoord);
            }

            if(riskScore < bestRiskScore){
                bestRiskScore = riskScore;
                bestMove = move;
            }
        }

        return bestMove;
    }

    @Override
    public boolean wantToFight( int[] enemyAbilities ) {
        //I don't want to fight :(
        return false;
    }

    //Return Manhattan Distance
    private int getDistance(int x, int y, Point to){
        return Math.abs(x-(int)to.getX())+Math.abs(y-(int)to.getY());
    }
}