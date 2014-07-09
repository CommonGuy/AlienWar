package alien;
import planet.Move;

public class FunkyJack extends Alien {
    public void setAbilityPoints(float[] abilities) {
        abilities[0] = 4.5f;
        abilities[1] = 1.5f;
        abilities[3] = 4;
    }

    private int QtyInRange(char[][] fields, int x, int y, int numStepsOut, char specie)
    {
        int count = 0;
        for(int i = numStepsOut * -1; i <= numStepsOut; i++)
            for(int j = numStepsOut * -1; j <= numStepsOut; j++)
                if(fields[x+i][y+j] == specie)
                    count++;
        return count;
    }

    private int AssessSquare(char[][] fields, int x, int y, int visibility, int prevScore){
        int score = 0;
        score += -10000 * QtyInRange(fields, x, y, visibility, 'A');                
        if(visibility > 0)
            score = AssessSquare(fields, x, y, visibility - 1, ((score + prevScore) / 5));
        else
            score += prevScore;

        return score;
    }

    public Move move(char[][] fields)   {
        int vision = getVisionFieldsCount();
        Move bestMove = Move.STAY;
        int bestMoveScore = AssessSquare(fields, vision, vision, vision - 1, 0);

        for (Move move : Move.values()) {
            int squareScore = AssessSquare(fields, vision + move.getXOffset(), vision + move.getYOffset(), vision - 1, 0);
            if(squareScore > bestMoveScore)
            {
                bestMoveScore = squareScore;
                bestMove = move;
            }
        }
        return bestMove;
    }

    public boolean wantToFight(int[] enemyAbilities)    {
        return false;
    }
}