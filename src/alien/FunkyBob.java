package alien;
import planet.Move;

public class FunkyBob extends Alien {
    public void setAbilityPoints(float[] abilities) {
        abilities[0] = 2.5f;
        abilities[1] = 5.5f;
        abilities[3] = 2;
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

    private int AssessSquare(char[][] fields, int x, int y, int visibility){
        int score = 0;

        for(int i = 0; i <= visibility; i++)
        {
            score += (-1000 / (i == 0 ? 0.3 : i)) * QtyInRange(fields, x, y, i, 'A');
            score += (100 / (i == 0 ? 0.3 : i)) * QtyInRange(fields, x, y, i, 'T');
            score += (100 / (i == 0 ? 0.3 : i)) * QtyInRange(fields, x, y, i, 'H');
            score += (100 / (i == 0 ? 0.3 : i)) * QtyInRange(fields, x, y, i, 'E');
            score += (50 / (i == 0 ? 0.3 : i)) * QtyInRange(fields, x, y, i, 'W');
            score += (50 / (i == 0 ? 0.3 : i)) * QtyInRange(fields, x, y, i, 'C');
        }

        return score;
    }

    public Move move(char[][] fields)   {
        int vision = getVisionFieldsCount();
        Move bestMove = Move.STAY;
        int bestMoveScore = AssessSquare(fields, vision, vision, vision - 1);

        for (Move move : Move.values()) {
            int squareScore = AssessSquare(fields, vision + move.getXOffset(), vision + move.getYOffset(), vision - 1);
            if(squareScore > bestMoveScore)
            {
                bestMoveScore = squareScore;
                bestMove = move;
            }

        }
        return bestMove;
    }

    public boolean wantToFight(int[] enemyAbilities)    {
        return ((getCurrentHp() + this.getStrengthLvl()) / 2) >
                ((enemyAbilities[0] * 3) + enemyAbilities[1]);
    }
}